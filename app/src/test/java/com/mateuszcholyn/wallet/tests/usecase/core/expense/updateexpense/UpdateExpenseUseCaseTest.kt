package com.mateuszcholyn.wallet.tests.usecase.core.expense.updateexpense

import com.mateuszcholyn.wallet.randomAmount
import com.mateuszcholyn.wallet.randomDescription
import com.mateuszcholyn.wallet.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.updateExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class UpdateExpenseUseCaseTest {

    @Test
    fun `should update expense`() {
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

        // when
        val updatedExpense =
            manager.updateExpenseUseCase {
                existingExpenseId = expenseScope.expenseId
                newCategoryId = newCategoryScope.categoryId
                newPaidAt = givenNewPaidAt
                newAmount = givenNewAmount
                newDescription = givenNewDescription
            }

        // then
        updatedExpense.validate {
            paidAtEqualTo(givenNewPaidAt)
            amountEqualTo(givenNewAmount)
            descriptionEqualTo(givenNewDescription)
            categoryIdEqualTo(newCategoryScope.categoryId)
        }
        manager.validate(updatedExpense.id) {
            paidAtEqualTo(givenNewPaidAt)
            amountEqualTo(givenNewAmount)
            descriptionEqualTo(givenNewDescription)
            categoryIdEqualTo(newCategoryScope.categoryId)
        }
    }

}