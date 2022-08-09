package com.mateuszcholyn.wallet.tests.usecase.searchservice


import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.setup.initExpenseAppManager

import org.junit.Test


class SearchServiceUseCaseAmountTest {

    @Test
    fun shouldIgnoreExpensesWhereAmountIsLowerThanToAmountSearchCriteria() {
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
    fun shouldIgnoreExpensesWhereAmountIsGreaterThanToAmountSearchCriteria() {
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