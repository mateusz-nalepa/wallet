package com.mateuszcholyn.wallet.tests.usecase.searchservice

import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.searchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class SearchServiceUseCaseCategoryTest {

    @Test
    fun `should search only given category id`() {
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
    fun `should search zero expenses when searching by non existing category`() {
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