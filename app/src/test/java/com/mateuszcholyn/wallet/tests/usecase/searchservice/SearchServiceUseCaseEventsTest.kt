package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.tests.manager.randomAmount
import com.mateuszcholyn.wallet.tests.manager.randomDescription
import com.mateuszcholyn.wallet.tests.manager.randomInt
import com.mateuszcholyn.wallet.tests.manager.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.updateExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
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
    fun `search service should handle expense updated event`() {
        // given
        val givenNewPaidAt = randomPaidAt()
        val givenNewAmount = randomAmount()
        val givenNewDescription = randomDescription()

        lateinit var newCategoryScope: CategoryScope
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                category {
                    expenseScope = expense { }
                }
                newCategoryScope = category { }
            }

        manager.updateExpenseUseCase {
            existingExpenseId = expenseScope.expenseId
            newCategoryId = newCategoryScope.categoryId
            newPaidAt = givenNewPaidAt
            newAmount = givenNewAmount
            newDescription = givenNewDescription
        }

        // when
        val searchServiceResult = manager.searchServiceUseCase { }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
            expenseIndex(0) {
                paidAtEqualTo(givenNewPaidAt)
                amountEqualTo(givenNewAmount)
                idEqualTo(expenseScope.expenseId)
                categoryIdEqualTo(newCategoryScope.categoryId)
            }
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