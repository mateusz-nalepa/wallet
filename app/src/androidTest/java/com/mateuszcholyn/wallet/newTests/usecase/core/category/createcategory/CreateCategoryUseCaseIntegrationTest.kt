package com.mateuszcholyn.wallet.newTests.usecase.core.category.createcategory

import com.mateuszcholyn.wallet.newTests.BaseIntegrationTest
import com.mateuszcholyn.wallet.tests.manager.ext.createCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class CreateCategoryUseCaseIntegrationTest : BaseIntegrationTest() {

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