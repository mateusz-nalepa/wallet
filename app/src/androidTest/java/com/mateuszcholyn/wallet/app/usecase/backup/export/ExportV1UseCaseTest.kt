package com.mateuszcholyn.wallet.app.usecase.backup.export

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
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
            backupWalletV1.categories.first().expenses.first(),
            backupWalletV1.categories.first().id.let { CategoryId(it) },
            manager.getAllCoreExpenses().first(),
        )
        assertExportedExpenseEqualToExpenseFromDb(
            backupWalletV1.categories.last().expenses.first(),
            backupWalletV1.categories.last().id.let { CategoryId(it) },
            manager.getAllCoreExpenses().last(),
        )
    }

    @Test
    fun exportedDataContainsExpensePaidAtInLongFormat() {
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
        val expensePaidAt = backupWalletV1.categories.first().expenses.first().paidAt

        assert(
            expensePaidAt == InstantConverter.toLong(firstExpense.paidAt),
        ) {
            """Expected date as: ${InstantConverter.toLong(firstExpense.paidAt)}.
                Got: $expensePaidAt
                """
        }
    }
}


fun assertExportedCategoryEqualToCategoryFromDb(
    exportedExpense: BackupWalletV1.BackupCategoryV1,
    categoryV2: CategoryV2,
) {
    assert(exportedExpense.id == categoryV2.id.id) { "categoryId not equal" }
    assert(exportedExpense.name == categoryV2.name) { "categoryName not equal" }
}

fun assertExportedExpenseEqualToExpenseFromDb(
    exportedExpense: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
    categoryId: CategoryId,
    expenseFromDb: ExpenseV2,
) {
    assert(exportedExpense.expenseId == expenseFromDb.expenseId.id) { "expenseId not equal" }
    assert(exportedExpense.amount == expenseFromDb.amount) { "amount not equal" }
    assert(exportedExpense.description == expenseFromDb.description) { "description not equal" }
    assert(exportedExpense.paidAt == InstantConverter.toLong(expenseFromDb.paidAt)) { "paidAt not equal" }
    // TODO: check category? XD
    assert(categoryId == expenseFromDb.categoryId) { "categoryId not equal" }
}
