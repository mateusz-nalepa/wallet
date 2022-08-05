package com.mateuszcholyn.wallet.tests.usecase.addexpense

import com.mateuszcholyn.wallet.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.ext.addExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class AddExpenseUseCaseTest {

    @Test
    fun `should add expense`() {
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
        manager.validate(newExpense.id) {
            paidAtEqualTo(givenPaidAt)
        }
    }

}