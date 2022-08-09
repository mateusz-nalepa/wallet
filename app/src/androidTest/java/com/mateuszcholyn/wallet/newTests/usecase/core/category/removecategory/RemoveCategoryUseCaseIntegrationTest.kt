package com.mateuszcholyn.wallet.newTests.usecase.core.category.removecategory

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryHasExpensesException
import com.mateuszcholyn.wallet.tests.catchThrowable
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.expense
import com.mateuszcholyn.wallet.tests.manager.ext.removeCategoryUseCase
import com.mateuszcholyn.wallet.tests.validate
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
        throwable.validate {
            isInstanceOf(CategoryHasExpensesException::class)
            hasMessage("Category with id ${categoryScope.categoryId.id} has expenses and cannot be removed")
        }
    }

}