package com.mateuszcholyn.wallet.app.usecase.searchservice


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.searchservice.searchServiceUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.validator.validate
import org.junit.Test


class SearchServiceUseCaseCategoryTest {

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