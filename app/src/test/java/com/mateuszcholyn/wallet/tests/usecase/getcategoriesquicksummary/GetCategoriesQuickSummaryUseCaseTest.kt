package com.mateuszcholyn.wallet.tests.usecase.getcategoriesquicksummary

import com.mateuszcholyn.wallet.tests.manager.randomInt
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.getCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.removeCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.updateExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import org.junit.Test

class GetCategoriesQuickSummaryUseCaseTest {

    @Test
    fun `quick summary should have information about added category`() {
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
    fun `quick summary should have information about added expenses`() {
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
            }
        }
    }

    @Test
    fun `quick summary should have information about expense removed event`() {
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
    fun `quick summary should not have information about category after update expense event`() {
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
            }
            categoryId(newCategoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(1)
            }
        }
    }

    @Test
    fun `quick summary should not have information about category after category removed event`() {
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