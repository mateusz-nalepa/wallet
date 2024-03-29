package com.mateuszcholyn.wallet.app.usecase.searchservice

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseCategoryIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldSearchOnlyGivenCategoryId() {
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
    fun shouldSearchZeroExpensesWhenSearchingByNonExistingCategory() {
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

}