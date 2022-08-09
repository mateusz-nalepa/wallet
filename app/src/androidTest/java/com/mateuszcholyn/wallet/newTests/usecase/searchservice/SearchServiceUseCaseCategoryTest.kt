package com.mateuszcholyn.wallet.newTests.usecase.searchservice

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchServiceUseCaseCategoryTest : BaseIntegrationTest() {

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