package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.CategoryFinished
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SavedCategoryFromDb
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SkipCategoryAndAllExpenses
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.CompletableDeferred
import java.util.UUID


class ImportV1UseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val allExpensesRemover: AllExpensesRemover,
) : UseCase {

    // TODO: odpal w transakcji i generalnie zrób sealed klase ImportResult
    suspend fun invoke(importV1Parameters: ImportV1Parameters): ImportV1Summary {
        return unsafeImportAllData(importV1Parameters)
    }

    private suspend fun unsafeImportAllData(
        importV1Parameters: ImportV1Parameters,
    ): ImportV1Summary {
        removeAllIfNecessary(importV1Parameters)

        val categories = importV1Parameters.backupWalletV1.categories

        val importV1SummaryGenerator =
            ImportV1SummaryGenerator(
                numberOfCategories = categories.size,
                numberOfExpenses = categories.flatMap { it.expenses }.size,
            )

        importV1Parameters
            .backupWalletV1
            .categories
            .map { backupCategory ->
                val categoryFinishedImporting =
                    getOrCreateCategoryById(
                        importV1Parameters,
                        backupCategory,
                        importV1SummaryGenerator,
                    )

                processCategoryFinishedImporting(
                    importV1Parameters,
                    backupCategory,
                    categoryFinishedImporting,
                    importV1SummaryGenerator,
                )
            }

        return importV1SummaryGenerator.toImportV1Summary()
    }

    private suspend fun processCategoryFinishedImporting(
        importV1Parameters: ImportV1Parameters,
        backupCategory: BackupWalletV1.BackupCategoryV1,
        categoryFinished: CategoryFinished,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        when (categoryFinished) {
            SkipCategoryAndAllExpenses -> {
                backupCategory.expenses.forEach { _ ->
                    importV1SummaryGenerator.markExpenseSkipped()
                }
            }

            is SavedCategoryFromDb -> backupCategory.expenses.forEach { backupExpense ->
                addExpense(
                    backupCategory,
                    importV1Parameters,
                    categoryFinished,
                    backupExpense,
                    importV1SummaryGenerator,
                )
            }
        }

    }

    private suspend fun getOrCreateCategoryById(
        importV1Parameters: ImportV1Parameters,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): CategoryFinished {


        val nullableCategoryFromDatabase =
            categoryCoreServiceAPI
                .getById(CategoryId(backupCategoryV1.id))
                ?: return run {
                    validateIdIsUUID(backupCategoryV1.id) {
                        "Invalid categoryId: [${backupCategoryV1.id}]. Have it been modified manually in file with backup data?"
                    }
                    addNewCategoryWhichHasBeenProbablyRemoved(
                        backupCategoryV1,
                        importV1SummaryGenerator
                    )
                }

        // tutaj jest sytuacja, że kategoria o danym id już istnieje
        return if (categoryChangedAfterExport(backupCategoryV1, nullableCategoryFromDatabase)) {
            askUserWhatToDoWhenCategoryNameChanged(
                backupCategoryV1,
                importV1SummaryGenerator,
                importV1Parameters,
            )
        } else {
            useExistingCategoryResult(backupCategoryV1, importV1SummaryGenerator)
        }
    }

    private fun validateIdIsUUID(id: String, lazyMessage: () -> String) {
        try {
            UUID.fromString(id)
        } catch (t: Throwable) {
            throw IllegalStateException(lazyMessage.invoke())
        }
    }

    private fun categoryChangedAfterExport(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        categoryFromDb: CategoryV2,
    ): Boolean =
        categoryFromDb.name != backupCategoryV1.name

    private fun addNewCategoryWhichHasBeenProbablyRemoved(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): SavedCategoryFromDb {
        val addedCategory =
            categoryCoreServiceAPI.add(
                CreateCategoryParameters(
                    categoryId = CategoryId(backupCategoryV1.id),
                    name = backupCategoryV1.name
                )
            )

        return SavedCategoryFromDb(
            categoryIdFromImportFile = CategoryId(backupCategoryV1.id),
            categoryIdFromDatabase = addedCategory.id,
            // TODO: ta linia wygląda na mega niepotrzebną
            name = backupCategoryV1.name,
        ).also {
            importV1SummaryGenerator.markCategoryImported()
        }
    }

    private fun useExistingCategoryResult(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): SavedCategoryFromDb =
        SavedCategoryFromDb(
            categoryIdFromImportFile = CategoryId(backupCategoryV1.id),
            categoryIdFromDatabase = CategoryId(backupCategoryV1.id),
            // TODO: ta linia wygląda na mega niepotrzebną
            name = backupCategoryV1.name,
        )
            .also { importV1SummaryGenerator.markCategorySkipped() }

    private suspend fun askUserWhatToDoWhenCategoryNameChanged(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
        importV1Parameters: ImportV1Parameters,
    ): CategoryFinished {
        val deferred = CompletableDeferred<CategoryFinished>()

//        val ifPreferredActionForAll....

        importV1Parameters
            .onCategoryChangedAction
            .invoke(
                OnCategoryChangedInput(
                    keepCategoryNameFromDatabase = {
                        deferred.complete(
                            useExistingCategoryResult(backupCategoryV1, importV1SummaryGenerator)
                        )
                    },
                    skipCategoryAndAllExpenses = {
                        importV1SummaryGenerator.markCategorySkipped()
                        deferred.complete(SkipCategoryAndAllExpenses)
                    },
                )
            )
        return deferred.await()
    }

    private suspend fun addExpense(
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
        importV1Parameters: ImportV1Parameters,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ) {
        val deferred = CompletableDeferred<Unit>()

        //        val ifPreferredActionForAll....

        importV1Parameters
            .onExpanseChangedAction
            .invoke(
                OnExpanseChangedInput(
                    keepExpenseFromDatabase = {
                        deferred.complete(
                            useExistingExpense(importV1SummaryGenerator)
                        )
                    },
                    skipExpense = {
                        deferred.complete(Unit)
                    },
                )
            )
        deferred.await()
    }

    private fun removeAllIfNecessary(importV1Parameters: ImportV1Parameters) {
        if (importV1Parameters.removeAllBeforeImport) {
            allExpensesRemover.removeAll()
        }
    }

}
