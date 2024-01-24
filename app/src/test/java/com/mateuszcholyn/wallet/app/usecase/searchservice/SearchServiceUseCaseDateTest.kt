package com.mateuszcholyn.wallet.app.usecase.searchservice


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.minusDays
import com.mateuszcholyn.wallet.util.localDateTimeUtils.plusDays
import com.mateuszcholyn.wallet.util.localDateTimeUtils.today
import org.junit.Test


class SearchServiceUseCaseDateTest {

    @Test
    fun searchServiceShouldIgnoreExpensesThatAreOlderThanSearchCriteria() {
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
    fun searchServiceShouldIgnoreExpensesThatAreNewerThanSearchCriteria() {
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

}