package com.mateuszcholyn.wallet.tests.usecase.getcategoriesquicksummary

import com.mateuszcholyn.wallet.randomInt
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.getCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class GetCategoriesQuickSummaryUseCaseTest {

    @Test
    fun `quick summary should have information about zero number of expenses`() {
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


}