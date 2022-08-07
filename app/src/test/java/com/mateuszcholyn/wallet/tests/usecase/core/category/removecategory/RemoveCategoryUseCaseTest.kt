package com.mateuszcholyn.wallet.tests.usecase.core.category.removecategory

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryHasExpensesException
import com.mateuszcholyn.wallet.catchThrowable
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.removeCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.validate
import org.junit.Test

class RemoveCategoryUseCaseTest {

    @Test
    fun `should throw exception when trying to remove category which has expenses`() {
        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    expense { }
                }
            }

        // when
        val throwable =
            catchThrowable {
                manager.removeCategoryUseCase {
                    categoryId = categoryScope.categoryId
                }
            }

        // then
        throwable.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${categoryScope.categoryId.id} has expenses and cannot be removed")
        }
    }


}