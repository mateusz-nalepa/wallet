package com.mateuszcholyn.wallet.app.usecase.core.category.createcategory


import com.mateuszcholyn.wallet.app.setupunittests.initExpenseAppManager
import com.mateuszcholyn.wallet.manager.ext.core.category.createCategoryUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.validate
import org.junit.Test


class CreateCategoryUseCaseTest {

    @Test
    fun shouldCreateCategory() {
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