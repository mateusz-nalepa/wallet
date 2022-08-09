package com.mateuszcholyn.wallet.newTests.usecase.core.expense.removeexpense

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseNotFoundException
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.ExpenseRemovedStatus
import com.mateuszcholyn.wallet.tests.catchThrowable
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.randomExpenseId
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class RemoveExpenseUseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldRemoveExpense() {
        // given
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                category {
                    expenseScope = expense { }
                }
            }

        // when
        val expenseRemovedStatus =
            manager.removeExpenseUseCase {
                expenseId = expenseScope.expenseId
            }

        // then
        expenseRemovedStatus.validate {
            statusEqualTo(ExpenseRemovedStatus.SUCCESS)
        }
        manager.validate {
            numberOfExpensesInExpenseCoreEqualTo(0)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToRemoveNonExistingCategory() {
        // given
        val nonExistingExpenseId = randomExpenseId()
        val manager =
            initExpenseAppManager {
                category {
                    expense { }
                }
            }

        // when
        val throwable =
            catchThrowable {
                manager.removeExpenseUseCase {
                    expenseId = nonExistingExpenseId
                }
            }

        // then
        throwable.validate {
            isInstanceOf(ExpenseNotFoundException::class)
            hasMessage("Expense with id ${nonExistingExpenseId.id} does not exist")
        }
    }

}