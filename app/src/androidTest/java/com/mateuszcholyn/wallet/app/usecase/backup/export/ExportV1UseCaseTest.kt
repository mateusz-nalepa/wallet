package com.mateuszcholyn.wallet.app.usecase.backup.export

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.app.usecase.backup.assertExportedCategoryEqualToCategoryFromDb
import com.mateuszcholyn.wallet.app.usecase.backup.assertExportedExpenseEqualToExpenseFromDb
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.backup.export.exportV1UseCase
import com.mateuszcholyn.wallet.manager.getAllCoreCategories
import com.mateuszcholyn.wallet.manager.getAllCoreExpenses
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class ExportV1UseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldExportV1Data() {
        // given
        val manager =
            initExpenseAppManager {
                category {
                    expense {}
                }
                category {
                    expense {}
                }
            }

        // when
        val backupWalletV1 = manager.exportV1UseCase()

        // then
        backupWalletV1.validate {
            versionIsEqualTo1()
            numberOfCategoriesEqualTo(2)
            numberOfExpensesEqualTo(2)
        }
        assertExportedCategoryEqualToCategoryFromDb(
            backupWalletV1.categories.first(),
            manager.getAllCoreCategories().first(),
        )

        assertExportedCategoryEqualToCategoryFromDb(
            backupWalletV1.categories.last(),
            manager.getAllCoreCategories().last(),
        )

        assertExportedExpenseEqualToExpenseFromDb(
            backupWalletV1.expenses.first(),
            manager.getAllCoreExpenses().first(),
        )
        assertExportedExpenseEqualToExpenseFromDb(
            backupWalletV1.expenses.last(),
            manager.getAllCoreExpenses().last(),
        )
    }


    @Test
    fun exportedDataContainsPaidAtInLongFormat() {
        // given
        lateinit var firstExpense: ExpenseScope

        val manager =
            initExpenseAppManager {
                category {
                    firstExpense = expense {}
                }
            }

        // when
        val backupWalletV1 = manager.exportV1UseCase()

        // then
        assert(
            backupWalletV1.expenses.first().paidAt == InstantConverter.toLong(firstExpense.paidAt),
        ) {
            """Expected date as: ${InstantConverter.toLong(firstExpense.paidAt)}.
                Got: ${backupWalletV1.expenses.first().paidAt}
                """
        }
    }
}
