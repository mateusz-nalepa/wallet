package com.mateuszcholyn.wallet.tests.usecase.core.category.createcategory

import com.mateuszcholyn.wallet.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.ext.createCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.initExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import org.junit.Test

class CreateCategoryUseCaseTest {

    @Test
    fun `should create category`() {
        // given
        val manager = initExpenseAppManager { }
        val givenCategoryName = randomCategoryName()

        // when
        val newCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        // then
        newCategory.validate {
            nameEqualTo(givenCategoryName)
        }

        manager.validate(newCategory.id) {
            nameEqualTo(givenCategoryName)
        }
    }

}