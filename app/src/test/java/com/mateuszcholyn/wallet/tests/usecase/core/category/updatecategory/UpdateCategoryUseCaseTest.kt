package com.mateuszcholyn.wallet.tests.usecase.core.category.updatecategory

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryNotFoundException
import com.mateuszcholyn.wallet.catchThrowable
import com.mateuszcholyn.wallet.randomCategoryId
import com.mateuszcholyn.wallet.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.CategoryScope
import com.mateuszcholyn.wallet.tests.manager.category
import com.mateuszcholyn.wallet.tests.manager.ext.updateCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.validate
import org.junit.Test

class UpdateCategoryUseCaseTest {

    @Test
    fun `should update expense`() {
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
    fun `should throw exception when trying to update non existing category`() {
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