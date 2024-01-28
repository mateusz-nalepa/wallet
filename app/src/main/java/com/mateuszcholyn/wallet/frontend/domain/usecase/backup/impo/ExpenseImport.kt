package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SavedCategoryFromDb
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.CompletableDeferred

class ExpenseImport(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) {

    suspend fun addExpense(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1Parameters: ImportV1Parameters,
        savedCategoryFromDb: SavedCategoryFromDb,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        // TODO: dodaj weryfikację dla kategorii, tzn, czy importowac expense czy nie

        val nullableExpenseFromDatabase =
            expenseCoreServiceAPI
                .getById(ExpenseId(backupExpenseV1.expenseId))
                ?: return run {
                    validateIdIsUUID(backupExpenseV1.expenseId) {
                        "Invalid expenseId: [${backupExpenseV1.expenseId}]. Have it been modified manually in file with backup data?"
                    }
                    addNewExpenseWhichWasProbablyRemoved(
                        backupCategoryV1,
                        savedCategoryFromDb,
                        backupExpenseV1,
                        importV1SummaryGenerator
                    )
                }

        if (expenseChangedAfterExport(
                backupCategoryV1,
                nullableExpenseFromDatabase,
                backupExpenseV1,
            )
        ) {
            askUserWhatToDoWhenExpenseChanged(
                nullableExpenseFromDatabase,
                backupCategoryV1,
                backupExpenseV1,
                importV1Parameters,
                importV1SummaryGenerator,
            )
        } else {
            useExistingExpense(importV1SummaryGenerator)
        }
    }

    private fun expenseChangedAfterExport(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        expenseV2: ExpenseV2,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
    ): Boolean =
        !expenseNotChangedAfterExport(
            backupCategoryV1,
            expenseV2,
            backupExpenseV1,
        )

    private fun expenseNotChangedAfterExport(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        expenseV2: ExpenseV2,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
    ): Boolean =
        expenseV2.categoryId.id == backupCategoryV1.id
                && InstantConverter.toLong(expenseV2.paidAt) == backupExpenseV1.paidAt
                && expenseV2.description == backupExpenseV1.description
                && expenseV2.amount == backupExpenseV1.amount


    private fun addNewExpenseWhichWasProbablyRemoved(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        savedCategoriesFromDb: SavedCategoryFromDb,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        AddExpenseParameters(
            expenseId = ExpenseId(backupExpenseV1.expenseId),
            amount = backupExpenseV1.amount,
            description = backupExpenseV1.description,
            paidAt = InstantConverter.toInstant(backupExpenseV1.paidAt),
            // TODO: tu wcześniej było wyszukiwanie na liście xd
            categoryId = savedCategoriesFromDb.categoryIdFromDatabase,
        )
            .let { expenseCoreServiceAPI.add(it) }
            .also { importV1SummaryGenerator.markExpenseImported() }
    }

    private fun useExistingExpense(
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        importV1SummaryGenerator.markExpenseSkipped()
    }

    private suspend fun askUserWhatToDoWhenExpenseChanged(
        existingExpenseFromDatabaseByExpenseId: ExpenseV2,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
        importV1Parameters: ImportV1Parameters,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        val deferred = CompletableDeferred<() -> Unit>()

        importV1Parameters
            .onExpanseChangedAction
            .invoke(
                OnExpanseChangedInput(
                    keepExpenseFromDatabase = {
                        deferred.complete {
                            useExistingExpense(importV1SummaryGenerator)
                        }
                    },
                    useExpenseFromBackup = {
                        deferred.complete {
                            updateExpenseByUsingDataFromBackup(
                                existingExpenseFromDatabaseByExpenseId,
                                backupCategoryV1,
                                backupExpenseV1,
                                importV1SummaryGenerator,
                            )
                        }
                    },
                )
            )
        deferred.await().invoke()
    }

    private fun updateExpenseByUsingDataFromBackup(
        existingExpenseFromDatabaseByExpenseId: ExpenseV2,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        backupExpenseV1: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {

        existingExpenseFromDatabaseByExpenseId
            .copy(
                amount = backupExpenseV1.amount,
                description = backupExpenseV1.description,
                paidAt = InstantConverter.toInstant(backupExpenseV1.paidAt),
                categoryId = CategoryId(backupCategoryV1.id)
            )
            .let { expenseCoreServiceAPI.update(it) }

        importV1SummaryGenerator.markExpenseImported()
    }


}