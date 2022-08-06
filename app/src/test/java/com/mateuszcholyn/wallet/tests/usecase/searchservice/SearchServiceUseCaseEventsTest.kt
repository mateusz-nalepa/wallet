package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.randomInt
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class SearchServiceUseCaseEventsTest {

    @Test
    fun `search service should have information about added expenses`() {
        // given
        val givenNumberOfExpenses = randomInt()
        val manager =
            initExpenseAppManager {
                category {
                    repeat(givenNumberOfExpenses) {
                        expense { }
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase { }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(givenNumberOfExpenses)
        }
    }

    @Test
    fun `search service should handle expense removed event`() {
        // given
        lateinit var existingExpense: ExpenseScope
        lateinit var removedExpense: ExpenseScope
        val manager =
            initExpenseAppManager {
                category {
                    existingExpense = expense { }
                    removedExpense = expense { }
                }
            }
        manager.removeExpenseUseCase {
            expenseId = removedExpense.expenseId
        }

        // when
        val searchServiceResult = manager.searchServiceUseCase { }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
            expenseIndex(0) { equalTo(existingExpense) }
        }
    }


}