package com.mateuszcholyn.wallet.app.usecase.core.category.createcategory

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.ext.core.category.createCategoryUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.manager.validator.validateCategoryFromDatabase
import dagger.hilt.android.testing.HiltAndroidTest
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

}