package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.randomInt
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class SearchServiceUseCaseTest {

    @Test
    fun `search service should have information about added expenses`() {
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
        val expensesList = manager.searchServiceUseCase()

        // then
        expensesList.validate {
            hasNumberOfExpensesEqualTo(givenNumberOfExpenses)
        }
    }

}