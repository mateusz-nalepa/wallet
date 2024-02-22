package com.mateuszcholyn.wallet.app.transaction

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManagerException
import com.mateuszcholyn.wallet.manager.randomCategory
import com.mateuszcholyn.wallet.manager.randomInt
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TransactionManagerTest : BaseIntegrationTest() {

    @Inject
    lateinit var transactionManager: TransactionManager

    @Inject
    lateinit var categoryRepository: CategoryRepository


    @Test
    fun `should not save any category when during transaction there is an error`() = runTest {
        // when
        val throwable =
            shouldThrow<RuntimeException> {
                transactionManager.runInTransaction {
                    categoryRepository.create(randomCategory())
                    categoryRepository.create(randomCategory())
                    throw RuntimeException()
                }
            }

        // then
        throwable::class.java shouldBe TransactionManagerException::class.java
        categoryRepository.getAllCategories().size shouldBe 0
    }

    @Test
    fun `should save categories to db`() = runTest {
        // given
        val givenNumberOfCategories = randomInt()

        // when
        transactionManager.runInTransaction {
            repeat(givenNumberOfCategories) {
                categoryRepository.create(randomCategory())
            }
        }

        // then
        categoryRepository.getAllCategories().size shouldBe givenNumberOfCategories
    }

}
