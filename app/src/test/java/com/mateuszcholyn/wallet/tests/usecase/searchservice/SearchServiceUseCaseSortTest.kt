package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.NewSort
import com.mateuszcholyn.wallet.tests.manager.plusInt
import com.mateuszcholyn.wallet.tests.manager.randomAmount
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.util.dateutils.plusIntDays
import com.mateuszcholyn.wallet.util.dateutils.today
import org.junit.Test

class SearchServiceUseCaseSortTest {

    @Test
    fun `should sort expenses by date descending`() {
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
            sort = NewSort(NewSort.Field.DATE, NewSort.Type.DESC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { paidAtEqualTo(today.plusIntDays(5)) }
            expenseIndex(1) { paidAtEqualTo(today.plusIntDays(3)) }
            expenseIndex(2) { paidAtEqualTo(today) }
        }
    }

    @Test
    fun `should sort expenses by date ascending`() {
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
            sort = NewSort(NewSort.Field.DATE, NewSort.Type.ASC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { paidAtEqualTo(today) }
            expenseIndex(1) { paidAtEqualTo(today.plusIntDays(3)) }
            expenseIndex(2) { paidAtEqualTo(today.plusIntDays(5)) }
        }
    }

    @Test
    fun `should sort expenses by amount descending`() {
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
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Type.DESC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { amountEqualTo(randomAmount.plusInt(5)) }
            expenseIndex(1) { amountEqualTo(randomAmount.plusInt(3)) }
            expenseIndex(2) { amountEqualTo(randomAmount) }
        }
    }

    @Test
    fun `should sort expenses by amount ascending`() {
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
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Type.ASC)
        }

        // then
        searchServiceResult.validate {
            expenseIndex(0) { amountEqualTo(randomAmount) }
            expenseIndex(1) { amountEqualTo(randomAmount.plusInt(3)) }
            expenseIndex(2) { amountEqualTo(randomAmount.plusInt(5)) }
        }
    }

}