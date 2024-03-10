package com.mateuszcholyn.wallet.app.usecase.getcategoriesquicksummary

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.core.category.createCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.category.removeCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.addExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.removeExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.updateExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.getcategoriesquicksummary.getCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.manager.ext.getcategoriesquicksummary.getCategoriesQuickSummaryUseCaseV2
import com.mateuszcholyn.wallet.manager.randomInt
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import org.junit.Test

@HiltAndroidTest
class GetCategoriesCategoryQuickSummaryUseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun quickSummaryShouldHaveInformationAboutAddedCategory() {

        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                }
            }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                doesNotHaveExpenses()
            }
        }
    }

    @Test
    fun quickSummaryShouldHaveInformationAboutCategoryAfterExpenseAddedEvent() {
        // given
        val givenNumberOfExpenses = randomInt()
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    repeat(givenNumberOfExpenses) {
                        expense { }
                    }
                }
            }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(givenNumberOfExpenses)
                hasName(categoryScope.categoryName)
            }
        }
    }

    @Test
    fun quickSummaryShouldHaveInformationAboutCategoryAfterExpenseRemovedEvent() {
        // given
        val givenNumberOfExpenses = randomInt()
        lateinit var categoryScope: CategoryScope
        lateinit var lastExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    repeat(givenNumberOfExpenses) {
                        lastExpenseScope = expense { }
                    }
                }
            }

        manager.removeExpenseUseCase {
            expenseId = lastExpenseScope.expenseId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(givenNumberOfExpenses - 1)
            }
        }
    }

    @Test
    fun quickSummaryShouldNotHaveInformationAboutCategoryAfterUpdateExpenseEvent() {
        // given
        lateinit var oldCategoryScope: CategoryScope
        lateinit var newCategoryScope: CategoryScope
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                oldCategoryScope = category {
                    expenseScope = expense {}
                }
                newCategoryScope = category {}
            }

        manager.updateExpenseUseCase {
            existingExpenseId = expenseScope.expenseId
            newCategoryId = newCategoryScope.categoryId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(oldCategoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(0)
                hasName(oldCategoryScope.categoryName)
            }
            categoryId(newCategoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(1)
                hasName(newCategoryScope.categoryName)
            }
        }
    }

    @Test
    fun quickSummaryShouldNotHaveInformationAboutCategoryAfterCategoryRemovedEvent() {
        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {}
            }

        manager.removeCategoryUseCase {
            categoryId = categoryScope.categoryId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryIdDoesNotExist(categoryScope.categoryId)
        }
    }

    @Test
    fun shouldReturnNumberOfExpensesPerMainCategoryAndSubCategories() {
        // given
        val manager = initExpenseAppManager { }
        val givenExpensesInMainCategory = randomInt()
        val givenExpensesInFirstSubCategory = randomInt()
        val givenExpensesInSecondSubCategory = randomInt()

        val mainCategory = manager.createCategoryUseCase {}
        repeat(givenExpensesInMainCategory) {
            manager.addExpenseUseCase {
                categoryId = mainCategory.id
            }
        }

        val firstSubCategory = manager.createCategoryUseCase {
            parentCategory = mainCategory
        }
        repeat(givenExpensesInFirstSubCategory) {
            manager.addExpenseUseCase {
                categoryId = firstSubCategory.id
            }
        }

        val secondSubCategory = manager.createCategoryUseCase {
            parentCategory = mainCategory
        }
        repeat(givenExpensesInSecondSubCategory) {
            manager.addExpenseUseCase {
                categoryId = secondSubCategory.id
            }
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCaseV2()

        // then
        quickSummaryList.quickSummaries.size shouldBe 1
        val mainCategorySummary = quickSummaryList.quickSummaries.first()
        mainCategorySummary.basicFieldsShouldEqualTo(
            mainCategory,
            givenExpensesInMainCategory + givenExpensesInFirstSubCategory + givenExpensesInSecondSubCategory
        )
        // then subcategories
        mainCategorySummary.subCategories.size shouldBe 2
        mainCategorySummary.subCategories.first()
            .basicFieldsShouldEqualTo(firstSubCategory, givenExpensesInFirstSubCategory)
        mainCategorySummary.subCategories.last()
            .basicFieldsShouldEqualTo(secondSubCategory, givenExpensesInSecondSubCategory)
    }

    private fun AbstractCategoryQuickSummary.basicFieldsShouldEqualTo(
        category: Category,
        numberOfExpenses: Int,
    ) {
        this.id shouldBe category.id
        this.name shouldBe category.name
        this.numberOfExpenses shouldBe numberOfExpenses.toLong()
    }

}