package com.mateuszcholyn.wallet.newTests.usecase.searchservice

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.util.dateutils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.dateutils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.dateutils.today
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseDateTest : BaseIntegrationTest() {

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