package com.mateuszcholyn.wallet.app.usecase.searchservice

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.plusInt
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.util.localDateTimeUtils.plusIntDays
import com.mateuszcholyn.wallet.util.localDateTimeUtils.today
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseSortIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldSortExpensesByDateDescending() {
        // given
        val today = today()
        val manager =
            initExpenseAppManager {
                category {
                    expense { paidAt = today.plusIntDays(5) }
                    expense { paidAt = today }
                    expense { paidAt = today.plusIntDays(3) }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            sort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { paidAtEqualTo(today.plusIntDays(5)) }
            expenseIndex(1) { paidAtEqualTo(today.plusIntDays(3)) }
            expenseIndex(2) { paidAtEqualTo(today) }
        }
    }

    @Test
    fun shouldSortExpensesByDateAscending() {
        // given
        val today = today()
        val manager =
            initExpenseAppManager {
                category {
                    expense { paidAt = today.plusIntDays(3) }
                    expense { paidAt = today }
                    expense { paidAt = today.plusIntDays(5) }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            sort = NewSort(NewSort.Field.DATE, NewSort.Order.ASC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { paidAtEqualTo(today) }
            expenseIndex(1) { paidAtEqualTo(today.plusIntDays(3)) }
            expenseIndex(2) { paidAtEqualTo(today.plusIntDays(5)) }
        }
    }

    @Test
    fun shouldSortExpensesByAmountDescending() {
        // given
        val randomAmount = randomAmount()
        val manager =
            initExpenseAppManager {
                category {
                    expense { amount = randomAmount.plusInt(3) }
                    expense { amount = randomAmount }
                    expense { amount = randomAmount.plusInt(5) }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.DESC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { amountEqualTo(randomAmount.plusInt(5)) }
            expenseIndex(1) { amountEqualTo(randomAmount.plusInt(3)) }
            expenseIndex(2) { amountEqualTo(randomAmount) }
        }
    }

    @Test
    fun shouldSortExpensesByAmountAscending() {
        // given
        val randomAmount = randomAmount()
        val manager =
            initExpenseAppManager {
                category {
                    expense { amount = randomAmount.plusInt(3) }
                    expense { amount = randomAmount }
                    expense { amount = randomAmount.plusInt(5) }
                }
            }

        // when
        val searchServiceResult = manager.searchServiceUseCase {
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.ASC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { amountEqualTo(randomAmount) }
            expenseIndex(1) { amountEqualTo(randomAmount.plusInt(3)) }
            expenseIndex(2) { amountEqualTo(randomAmount.plusInt(5)) }
        }
    }

}