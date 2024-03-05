package com.mateuszcholyn.wallet.app.usecase.core.category.updatecategory

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryNotFoundException
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.ext.core.category.updateCategoryUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.validate
import com.mateuszcholyn.wallet.manager.validator.validateCategoryFromDatabase
import com.mateuszcholyn.wallet.util.throwable.catchThrowable
import com.mateuszcholyn.wallet.util.throwable.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class UpdateCategoryUseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldUpdateExpense() {
        // given
        val givenNewCategoryName = randomCategoryName()

        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {}
            }

        // when
        val updatedCategory =
            manager.updateCategoryUseCase {
                existingCategoryId = categoryScope.categoryId
                newName = givenNewCategoryName
            }

        // then
        updatedCategory.validate {
            nameEqualTo(givenNewCategoryName)
        }
        manager.validateCategoryFromDatabase(updatedCategory.id) {
            nameEqualTo(givenNewCategoryName)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToUpdateNonExistingCategory() {
        // given
        val nonExistingCategoryId = randomCategoryId()
        val manager = initExpenseAppManager {}

        // when
        val throwable = catchThrowable {
            manager.updateCategoryUseCase {
                existingCategoryId = nonExistingCategoryId
            }
        }

        // then
        throwable.cause?.validate {
            isInstanceOf(CategoryNotFoundException::class)
            hasMessage("Category with id ${nonExistingCategoryId.id} does not exist")
        }
    }

}