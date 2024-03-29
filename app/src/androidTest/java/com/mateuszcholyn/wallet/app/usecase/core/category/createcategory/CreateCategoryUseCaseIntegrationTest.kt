package com.mateuszcholyn.wallet.app.usecase.core.category.createcategory

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.api.core.category.AbstractCategory
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryHasExpensesException
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.UnableToAddSubCategoryToExistingSubCategoryException
import com.mateuszcholyn.wallet.manager.ext.core.category.createCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.category.removeCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.addExpenseUseCase
import com.mateuszcholyn.wallet.manager.getAllCategoriesGrouped
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.manager.validator.validateCategoryFromDatabase
import com.mateuszcholyn.wallet.util.throwable.catchThrowable
import com.mateuszcholyn.wallet.util.throwable.validate
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import org.junit.Test

@HiltAndroidTest
class CreateCategoryUseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldCreateCategory() {
        // given
        val manager = initExpenseAppManager { }
        val givenCategoryName = randomCategoryName()

        // when
        val newCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        // then
        newCategory.validate {
            nameEqualTo(givenCategoryName)
        }

        manager.validateCategoryFromDatabase(newCategory.id) {
            nameEqualTo(givenCategoryName)
        }
    }

    @Test
    fun shouldCreateSecondCategoryWithSameName() {
        // given
        val manager = initExpenseAppManager { }
        val givenCategoryName = randomCategoryName()

        // when
        val firstCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        val secondCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        firstCategory.validate {
            nameEqualTo(givenCategoryName)
        }
        secondCategory.validate {
            nameEqualTo(givenCategoryName)
        }
    }

    @Test
    fun shouldCreateSubCategory() {
        // given
        val manager = initExpenseAppManager { }
        val givenMainCategoryName = "main category name"
        val givenSubCategoryName = "sub category name"

        // when
        val mainCategory =
            manager.createCategoryUseCase {
                name = givenMainCategoryName
            }

        val subCategoryFromUseCaseResponse =
            manager.createCategoryUseCase {
                name = givenSubCategoryName
                parentCategory = mainCategory
            }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        mainCategory.validate {
            nameEqualTo(givenMainCategoryName)
            parentCategoryIs(null)
        }
        subCategoryFromUseCaseResponse.validate {
            nameEqualTo(givenSubCategoryName)
            parentCategoryIs(mainCategory)
        }
        manager.validateCategoryFromDatabase(subCategoryFromUseCaseResponse.id) {
            nameEqualTo(givenSubCategoryName)
            parentCategoryIs(mainCategory)
        }
    }

    @Test
    fun shouldCreateMoreThanOneSubCategoryWithGivenParentCategory() {
        // given
        val manager = initExpenseAppManager { }
        val givenMainCategoryName = "main category name"
        val givenNumberOfSubCategories = 10

        // when
        val mainCategory =
            manager.createCategoryUseCase {
                name = givenMainCategoryName
            }

        repeat(givenNumberOfSubCategories) {
            manager.createCategoryUseCase {
                name = randomCategoryName()
                parentCategory = mainCategory
            }
        }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(givenNumberOfSubCategories + 1)
        }
    }

    // TODO: czy na pewno dobry exception? xd
    @Test
    fun shouldNotBeAbleToRemoveSubCategoryWhichHasExpenses() {
        // given
        val manager = initExpenseAppManager { }


        val mainCategory =
            manager.createCategoryUseCase {}

        val subCategory =
            manager.createCategoryUseCase {
                name = randomCategoryName()
                parentCategory = mainCategory
            }

        manager.addExpenseUseCase {
            this.categoryId = subCategory.id
        }

        // when
        val throwable =
            catchThrowable {
                manager.removeCategoryUseCase {
                    this.categoryId = subCategory.id
                }
            }


        // then
        throwable.cause?.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${subCategory.id.id} has expenses and cannot be removed")
        }
    }

    // TODO: czy na pewno dobry exception? xd
    @Test
    fun shouldNotRemoveMainCategoryWhenThereAreSubCategories() {
        // given
        val manager = initExpenseAppManager { }


        val mainCategory =
            manager.createCategoryUseCase {}

        manager.createCategoryUseCase {
            name = randomCategoryName()
            parentCategory = mainCategory
        }

        // when
        val throwable =
            catchThrowable {
                manager.removeCategoryUseCase {
                    this.categoryId = mainCategory.id
                }
            }

        // then
        throwable.cause?.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${mainCategory.id.id} has expenses and cannot be removed")
        }
    }

    // TODO: czy na pewno dobry exception? xd
    @Test
    fun shouldNotRemoveMainCategoryWhenSubCategoryHasExpenses() {
        // given
        val manager = initExpenseAppManager { }


        val mainCategory =
            manager.createCategoryUseCase {}

        val subCategory =
            manager.createCategoryUseCase {
                name = randomCategoryName()
                parentCategory = mainCategory
            }

        manager.addExpenseUseCase {
            this.categoryId = subCategory.id
        }

        // when
        val throwable =
            catchThrowable {
                manager.removeCategoryUseCase {
                    this.categoryId = mainCategory.id
                }
            }


        // then
        throwable.cause?.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${mainCategory.id.id} has expenses and cannot be removed")
        }
    }

    @Test
    fun shouldReturnCategoryWithSubCategories() {
        // given
        val manager = initExpenseAppManager { }

        val mainCategory = manager.createCategoryUseCase {}

        val firstSubCategory =
            manager.createCategoryUseCase {
                parentCategory = mainCategory
            }

        val secondSubCategory =
            manager.createCategoryUseCase {
                parentCategory = mainCategory
            }

        // when
        val categoriesGrouped = manager.getAllCategoriesGrouped()

        //  then
        categoriesGrouped.size shouldBe 1
        val firstMainCategory = categoriesGrouped.first()
        firstMainCategory basicFieldsShouldEqualTo mainCategory

        firstMainCategory.subCategories.size shouldBe 2
        firstMainCategory.subCategories.first() basicFieldsShouldEqualTo firstSubCategory
        firstMainCategory.subCategories.last() basicFieldsShouldEqualTo secondSubCategory
    }

    private infix fun AbstractCategory.basicFieldsShouldEqualTo(category: Category) {
        this.id shouldBe category.id
        this.name shouldBe category.name
    }

    @Test
    fun shouldNotAddSubCategoryToExistingSubCategory() {
        // given
        val manager = initExpenseAppManager { }

        val mainCategory = manager.createCategoryUseCase {}

        val subCategory =
            manager.createCategoryUseCase {
                parentCategory = mainCategory
            }

        // when
        val throwable =
            catchThrowable {
                manager.createCategoryUseCase {
                    parentCategory = subCategory
                }
            }


        // then
        throwable.cause?.validate {
            isInstanceOf(UnableToAddSubCategoryToExistingSubCategoryException::class)
            hasMessage("Unable to add subcategory to existing subcategory. Existing subCategoryId: ${subCategory.id.id}")
        }
    }

}