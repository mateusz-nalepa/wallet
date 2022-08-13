package com.mateuszcholyn.wallet.app.usecase.getcategoriesquicksummary


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.*
import com.mateuszcholyn.wallet.manager.ext.core.category.removeCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.removeExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.updateExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.getcategoriesquicksummary.getCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import org.junit.Test


class GetCategoriesCategoryQuickSummaryUseCaseTest {

    @Test
    fun quickSummaryShouldHaveInformationAboutAddedCategory() {

        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {}
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

}