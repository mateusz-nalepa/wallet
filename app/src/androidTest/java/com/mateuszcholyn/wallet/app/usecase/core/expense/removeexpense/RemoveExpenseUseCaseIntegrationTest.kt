package com.mateuszcholyn.wallet.app.usecase.core.expense.removeexpense

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseNotFoundException
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.ExpenseRemovedStatus
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.core.expense.removeExpenseUseCase
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.util.throwable.catchThrowable
import com.mateuszcholyn.wallet.util.throwable.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class RemoveExpenseUseCaseIntegrationTest : BaseIntegrationTest() {

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
            numberOfCoreExpensesEqualTo(0)
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
        throwable.cause?.validate {
            isInstanceOf(ExpenseNotFoundException::class)
            hasMessage("Expense with id ${nonExistingExpenseId.id} does not exist")
        }
    }

}