package com.mateuszcholyn.wallet.app.usecase.searchservice


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.*
import com.mateuszcholyn.wallet.manager.ext.core.expense.removeExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.updateExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import org.junit.Test


class SearchServiceUseCaseEventsTest {

    @Test
    fun searchServiceShouldHaveInformationAboutAddedExpenses() {
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
    fun searchServiceShouldHandleExpenseUpdatedEvent() {
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
                categoryNameEqualTo(newCategoryScope.categoryName)
            }
        }

    }

    @Test
    fun searchServiceShouldHandleExpenseRemovedEvent() {
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