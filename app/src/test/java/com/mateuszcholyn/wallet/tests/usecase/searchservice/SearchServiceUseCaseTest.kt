package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.randomInt
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
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
        val searchServiceResult = manager.searchServiceUseCase { }

        // then
        searchServiceResult.validate {
            hasNumberOfExpensesEqualTo(givenNumberOfExpenses)
        }
    }

    @Test
    fun `search service should have information about average expense result for single expense`() {
        // given
        val givenAmount = "10".toBigDecimal()
        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        amount = givenAmount
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {}

        // then
        searchServiceResult.validate {
            averageExpenseIs(givenAmount)
            numberOfDysEqualTo(1)
        }
    }

    @Test
    fun `search service should have information about average expense result for multiples expenses`() {
        // given
        val firstExpenseAmount = "10".toBigDecimal()
        val secondExpenseAmount = "20".toBigDecimal()
        val expensedExpenseAverageAmount = firstExpenseAmount + secondExpenseAmount

        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        amount = firstExpenseAmount
                    }
                    expense {
                        amount = secondExpenseAmount
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {}

        // then
        searchServiceResult.validate {
            averageExpenseIs(expensedExpenseAverageAmount)
            numberOfDysEqualTo(1)
        }
    }

}