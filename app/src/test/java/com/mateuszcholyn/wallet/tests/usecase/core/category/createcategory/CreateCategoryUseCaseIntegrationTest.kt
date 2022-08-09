package com.mateuszcholyn.wallet.tests.usecase.core.category.createcategory


import com.mateuszcholyn.wallet.tests.manager.ext.createCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import com.mateuszcholyn.wallet.tests.setup.initExpenseAppManager

import org.junit.Test


class CreateCategoryUseCaseIntegrationTest {

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