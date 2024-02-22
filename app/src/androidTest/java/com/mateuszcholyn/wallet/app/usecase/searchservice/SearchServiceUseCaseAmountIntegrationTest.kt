package com.mateuszcholyn.wallet.app.usecase.searchservice

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.minusRandomValue
import com.mateuszcholyn.wallet.manager.plusRandomValue
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseAmountIntegrationTest : BaseIntegrationTest() {

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