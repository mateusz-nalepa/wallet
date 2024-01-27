package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.backup.export.exportV1UseCase
import com.mateuszcholyn.wallet.manager.ext.backup.impo.importV1UseCase
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class BackupExportV1UseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldImportV1Data() {
        // given
        lateinit var firstCategory: CategoryScope
        lateinit var firstExpense: ExpenseScope

        lateinit var secondCategory: CategoryScope
        lateinit var secondExpense: ExpenseScope
        val manager =
            initExpenseAppManager {
                firstCategory = category {
                    firstExpense = expense {}
                }
                secondCategory = category {
                    secondExpense = expense {}
                }
            }
        val givenBackupWalletV1 = manager.exportV1UseCase()

        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
            numberOfCoreExpensesEqualTo(2)
        }
    }

    @Test
    fun importedDataContainsPaidAtInInstantFormat() {
        // given
        lateinit var firstExpense: ExpenseScope

        val manager =
            initExpenseAppManager {
                category {
                    firstExpense = expense {}
                }
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }


        // then
        val expenseFromDb =
            manager
                .expenseAppDependencies
                .expenseRepositoryV2
                .getAllExpenses()
                .first()

        assert(
            expenseFromDb.paidAt == InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt),
        ) {
            """Expected date as: ${InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt)}.
                Got: ${expenseFromDb.paidAt}
                """
        }
    }


}

fun BackupWalletV1.validate(validateBlock: BackupV1WalletValidator.() -> Unit) {
    BackupV1WalletValidator(this)
        .validateBlock()
}

class BackupV1WalletValidator(
    private val backupWalletV1: BackupWalletV1,
) {

    fun versionIsEqualTo1() {
        assert(backupWalletV1.version == 1) {
            "Backup version should be: 1. Actual: ${backupWalletV1.version} "
        }
    }

    fun numberOfCategoriesEqualTo(expectedNumberOfCategories: Int) {
        assert(backupWalletV1.categories.size == 2) {
            "Categories in backup should be: 2, Actual: ${backupWalletV1.categories.size} "
        }
    }

    fun numberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(backupWalletV1.expenses.size == 2) {
            "Expenses in backup should be: 2, Actual: ${backupWalletV1.expenses.size} "
        }
    }

}

