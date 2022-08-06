package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.*
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.util.dateutils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.dateutils.atStartOfTheDay
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
        val searchServiceResult = manager.searchServiceUseCase { }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(givenNumberOfExpenses)
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
            numberOfDaysEqualTo(1)
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
            numberOfDaysEqualTo(numberOfDays)
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
            numberOfDaysEqualTo(1)
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
    fun `search service should ignore expenses that are older than search criteria`() {
        // given
        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        paidAt = today()
                    }
                    expense {
                        paidAt = today().minusDays(2)
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            beginDate = today().atStartOfTheDay()
            endDate = today().atEndOfTheDay()
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
        }
    }

    @Test
    fun `search service should ignore expenses that are newer than search criteria`() {
        // given
        val manager =
            initExpenseAppManager {
                category {
                    expense {
                        paidAt = today()
                    }
                    expense {
                        paidAt = today().plusDays(2)
                    }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            beginDate = today().atStartOfTheDay()
            endDate = today().atEndOfTheDay()
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
        }
    }

    @Test
    fun `should search only given category id`() {
        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    expense {}
                    expense {}
                }
                category {
                    expense {}
                    expense {}
                    expense {}
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            categoryId = categoryScope.categoryId
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(2)
        }
    }

    @Test
    fun `should search zero expenses when searching by non existing category`() {
        // given
        val manager =
            initExpenseAppManager {
                category {
                    expense {}
                    expense {}
                }
                category {
                    expense {}
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            categoryId = randomCategoryId()
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(0)
        }
    }

    @Test
    fun `should ignore expenses where amount is less than fromAmount search criteria`() {
        // given
        val amountSearchCriteria = randomAmount()
        val manager =
            initExpenseAppManager {
                category {
                    expense { amount = amountSearchCriteria.plusRandomValue() }
                    expense { amount = amountSearchCriteria.minusRandomValue() }
                    expense { amount = amountSearchCriteria.minusRandomValue() }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            fromAmount = amountSearchCriteria
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
        }
    }

    @Test
    fun `should ignore expenses where amount is greater than toAmount search criteria`() {
        // given
        val amountSearchCriteria = randomAmount()
        val manager =
            initExpenseAppManager {
                category {
                    expense { amount = amountSearchCriteria.plusRandomValue() }
                    expense { amount = amountSearchCriteria.plusRandomValue() }
                    expense { amount = amountSearchCriteria.minusRandomValue() }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            toAmount = amountSearchCriteria
        }

        // then
        searchServiceResult.validate {
            numberOfExpensesEqualTo(1)
        }
    }

}