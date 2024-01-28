package com.mateuszcholyn.wallet.app.usecase.core.category.createcategory

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.manager.ext.core.category.createCategoryUseCase
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.validate
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

    @Test
    fun shouldCreateSecondCategoryWithSameName() {
        // given
        val manager = initExpenseAppManager { }
        val givenCategoryName = randomCategoryName()

        // when
        val firstCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        val secondCategory =
            manager.createCategoryUseCase {
                name = givenCategoryName
            }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        firstCategory.validate {
            nameEqualTo(givenCategoryName)
        }
        secondCategory.validate {
            nameEqualTo(givenCategoryName)
        }
    }


}