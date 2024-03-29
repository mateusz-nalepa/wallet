package com.mateuszcholyn.wallet.app.usecase.searchservice


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.util.localDateTimeUtils.*
import org.junit.Test


class SearchServiceUseCaseAverageExpenseTest {

    @Test
    fun searchServiceShouldHaveInformationAboutAverageExpenseResultForSingleExpense() {
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
            numberOfDaysEqualTo(1)
        }
    }

    @Test
    fun searchServiceShouldReturnAverageExpenseAwareThatExpenseWerePaidFewDaysAgo() {
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
            numberOfDaysEqualTo(numberOfDays)
        }
    }

    @Test
    fun searchServiceShouldHaveInformationAboutAverageExpenseResultForMultiplesExpenses() {
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
            numberOfDaysEqualTo(1)
        }
    }

    @Test
    fun searchServiceShouldHaveInformationAboutAverageExpenseResultForMultiplesExpensesInMultiplesDays() {
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
                        paidAt = today()
                        amount = secondExpenseAmount
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {}

        // then
        searchServiceResult.validate {
            averageExpenseIs(expensedExpenseAverageAmount)
            numberOfDaysEqualTo(1)
        }
    }

    @Test
    fun searchServiceShouldTakeIntoAccountLastFewDaysWithoutExpenses() {
        // given
        val givenAmount = "15".toBigDecimal()
        val expectedNumberOfDays = 3
        val expectedAverageAmount = "10".toBigDecimal()

        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        paidAt = today().minusIntDays(2)
                        amount = givenAmount
                    }
                    expense {
                        paidAt = today().minusIntDays(1)
                        amount = givenAmount
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            endDate = today()
        }

        // then
        searchServiceResult.validate {
            averageExpenseIs(expectedAverageAmount)
            numberOfDaysEqualTo(expectedNumberOfDays)
        }
    }

}