package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.CompletableDeferred

class ExpenseImport(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val backupCategoryV1: BackupWalletV1.BackupCategoryV1,
    private val importV1Parameters: ImportV1Parameters,
    private val savedCategoryFromDb: SavedCategoryFromDb,
    private val backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
    private val importV1SummaryGenerator: ImportV1SummaryGenerator,
) {

    suspend fun addExpense() {
        val expenseFromDb =
            expenseCoreServiceAPI
                .getById(ExpenseId(backupExpenseV1.expenseId))
                ?: return addNewExpenseWhichWasProbablyRemoved()


        if (expenseChangedAfterExport(expenseFromDb)) {
            askUserWhatToDoWhenExpenseChanged(expenseFromDb)
        } else {
            useExistingExpense(importV1SummaryGenerator)
        }
    }

    private fun expenseChangedAfterExport(expenseFromDb: ExpenseV2): Boolean =
        !expenseNotChangedAfterExport(expenseFromDb)

    private fun expenseNotChangedAfterExport(expenseFromDb: ExpenseV2): Boolean =
        expenseFromDb.categoryId.id == backupCategoryV1.id
                && InstantConverter.toLong(expenseFromDb.paidAt) == backupExpenseV1.paidAt
                && expenseFromDb.description == backupExpenseV1.description
                && expenseFromDb.amount == backupExpenseV1.amount


    private suspend fun addNewExpenseWhichWasProbablyRemoved() {
        validateIdIsUUID(backupExpenseV1.expenseId) {
            "Invalid expenseId: [${backupExpenseV1.expenseId}]. Have it been modified manually in file with backup data?"
        }

        AddExpenseParameters(
            expenseId = ExpenseId(backupExpenseV1.expenseId),
            amount = backupExpenseV1.amount,
            description = backupExpenseV1.description,
            paidAt = InstantConverter.toInstant(backupExpenseV1.paidAt),
            categoryId = savedCategoryFromDb.categoryId,
        )
            .let { expenseCoreServiceAPI.add(it) }
            .also { importV1SummaryGenerator.markExpenseImported() }
    }

    private fun useExistingExpense(importV1SummaryGenerator: ImportV1SummaryGenerator) {
        importV1SummaryGenerator.markExpenseSkipped()
    }

    private suspend fun askUserWhatToDoWhenExpenseChanged(expenseFromDb: ExpenseV2) {
        val deferred = CompletableDeferred<suspend () -> Unit>()

        importV1Parameters
            .onExpanseChangedAction
            .invoke(
                OnExpanseChangedInput(
                    keepExpenseFromDatabase = {
                        deferred.complete { useExistingExpense(importV1SummaryGenerator) }
                    },
                    useExpenseFromBackup = {
                        deferred.complete { updateExpenseByUsingDataFromBackup(expenseFromDb) }
                    },
                )
            )
        deferred.await().invoke()
    }

    private suspend fun updateExpenseByUsingDataFromBackup(expenseFromDb: ExpenseV2) {
        expenseFromDb
            .copy(
                amount = backupExpenseV1.amount,
                description = backupExpenseV1.description,
                paidAt = InstantConverter.toInstant(backupExpenseV1.paidAt),
                categoryId = CategoryId(backupCategoryV1.id)
            )
            .let { expenseCoreServiceAPI.update(it) }
            .also { importV1SummaryGenerator.markExpenseImported() }
    }

}
