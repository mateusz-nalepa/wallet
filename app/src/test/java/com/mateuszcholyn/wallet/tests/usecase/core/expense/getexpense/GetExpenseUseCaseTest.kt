package com.mateuszcholyn.wallet.tests.usecase.core.expense.getexpense


import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseNotFoundException
import com.mateuszcholyn.wallet.tests.catchThrowable
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.getExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.setup.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.validate
import org.junit.Test


class GetExpenseUseCaseTest {

    @Test
    fun shouldGetExpenseWithCategory() {
        // given
        lateinit var categoryScope: CategoryScope
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    expenseScope = expense { }
                }
            }

        // when
        val expenseWithCategory =
            manager.getExpenseUseCase {
                expenseId = expenseScope.expenseId
            }

        // then
        expenseWithCategory.validate {
            equalTo(categoryScope, expenseScope)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToGetNonExistingExpense() {
        // given
        val nonExistingExpenseId = randomExpenseId()
        val manager = initExpenseAppManager {}

        // when
        val throwable = catchThrowable {
            manager.getExpenseUseCase {
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