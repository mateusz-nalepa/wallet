package com.mateuszcholyn.wallet.tests.usecase.removeexpense

import com.mateuszcholyn.wallet.backend.core.ExpenseNotFoundException
import com.mateuszcholyn.wallet.catchThrowable
import com.mateuszcholyn.wallet.randomExpenseId
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.usecase.ExpenseRemovedStatus
import com.mateuszcholyn.wallet.validate
import org.junit.Test

class RemoveExpenseUseCaseTest {

    @Test
    fun `should remove expense`() {
        // given
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                category {
                    expense { }
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
    fun `should throw exception when trying to remove non existing category`() {
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