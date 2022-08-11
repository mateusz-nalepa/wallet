package com.mateuszcholyn.wallet.tests.usecase.core.expense.addexpense


import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.ext.addExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.setup.initExpenseAppManager

import org.junit.Test


class AddExpenseUseCaseScopeTest {

    @Test
    fun shouldAddExpense() {
        // given
        val givenPaidAt = randomPaidAt()
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {}
            }

        // when
        val newExpense =
            manager.addExpenseUseCase {
                categoryId = categoryScope.categoryId
                paidAt = givenPaidAt
            }

        // then
        newExpense.validate {
            paidAtEqualTo(givenPaidAt)
        }
        manager.validate(newExpense.expenseId) {
            paidAtEqualTo(givenPaidAt)
        }
    }

}