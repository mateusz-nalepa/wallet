package com.mateuszcholyn.wallet.app.usecase.core.expense.getexpense


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseNotFoundException
import com.mateuszcholyn.wallet.manager.*
import com.mateuszcholyn.wallet.manager.ext.core.expense.getExpenseUseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.util.throwable.catchThrowable
import com.mateuszcholyn.wallet.util.throwable.validate
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