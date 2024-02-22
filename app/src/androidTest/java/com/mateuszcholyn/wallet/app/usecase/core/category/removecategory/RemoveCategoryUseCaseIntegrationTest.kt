package com.mateuszcholyn.wallet.app.usecase.core.category.removecategory

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryHasExpensesException
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.core.category.removeCategoryUseCase
import com.mateuszcholyn.wallet.util.throwable.catchThrowable
import com.mateuszcholyn.wallet.util.throwable.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class RemoveCategoryUseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldThrowExceptionWhenTryingToRemoveCategoryWhichHasExpenses() {
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
        throwable.cause?.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${categoryScope.categoryId.id} has expenses and cannot be removed")
        }
    }

}