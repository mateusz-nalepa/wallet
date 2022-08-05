package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.randomInt
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.util.dateutils.today
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
        val searchServiceResult = manager.searchServiceUseCase {
            beginDate = today()
        }

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
    fun `search service should return average expense aware that expense were paid few days ago`() {
        // given
        val expenseAmount = "30".toBigDecimal()
        val expectedExpenseAmount = "10".toBigDecimal()
        val numberOfDays = 3
        val today = today()
        val threeDaysAgo = today.minusDays(numberOfDays.toLong())

        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        amount = expenseAmount
                        paidAt = today
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            beginDate = threeDaysAgo
            endDate = today
        }

        // then
        searchServiceResult.validate {
            averageExpenseIs(expectedExpenseAmount)
            numberOfDysEqualTo(numberOfDays)
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

    @Test
    fun `search service should have information about average expense result for multiples expenses in multiples days`() {
        // given
        val firstExpenseAmount = "10".toBigDecimal()
        val secondExpenseAmount = "20".toBigDecimal()
        val expensedExpenseAverageAmount = firstExpenseAmount + secondExpenseAmount

        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        paidAt = today()
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