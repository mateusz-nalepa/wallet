package com.mateuszcholyn.wallet.newTests.usecase.searchservice

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseAmountTest : BaseIntegrationTest() {

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