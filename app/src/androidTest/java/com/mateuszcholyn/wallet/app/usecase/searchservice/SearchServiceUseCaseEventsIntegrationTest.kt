package com.mateuszcholyn.wallet.app.usecase.searchservice

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.core.expense.removeExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.updateExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.randomDescription
import com.mateuszcholyn.wallet.manager.randomInt
import com.mateuszcholyn.wallet.manager.randomPaidAt
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseEventsIntegrationTest : BaseIntegrationTest() {

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