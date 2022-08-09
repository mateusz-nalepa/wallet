package com.mateuszcholyn.wallet.newTests.usecase.core.category.updatecategory

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryNotFoundException
import com.mateuszcholyn.wallet.tests.catchThrowable
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.ext.updateCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.validate
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
        manager.validate(updatedCategory.id) {
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
        throwable.validate {
            isInstanceOf(CategoryNotFoundException::class)
            hasMessage("Category with id ${nonExistingCategoryId.id} does not exist")
        }
    }

}