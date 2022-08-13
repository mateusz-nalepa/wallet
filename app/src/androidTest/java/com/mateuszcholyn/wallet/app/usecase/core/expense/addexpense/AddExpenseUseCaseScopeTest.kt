package com.mateuszcholyn.wallet.app.usecase.core.expense.addexpense

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.ext.core.expense.addExpenseUseCase
import com.mateuszcholyn.wallet.manager.randomPaidAt
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class AddExpenseUseCaseScopeTest : BaseIntegrationTest() {

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