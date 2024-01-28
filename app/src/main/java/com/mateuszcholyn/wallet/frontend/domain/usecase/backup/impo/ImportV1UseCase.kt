package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.CategoryFinished
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SavedCategoryFromDb
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SkipCategoryAndAllExpenses
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
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
                    CategoryImport(categoryCoreServiceAPI)
                        .getOrCreateCategoryById(
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
            is SkipCategoryAndAllExpenses -> {
                backupCategory.expenses.forEach { _ ->
                    importV1SummaryGenerator.markExpenseSkipped()
                }
            }

            is SavedCategoryFromDb -> backupCategory.expenses.forEach { backupExpense ->
                ExpenseImport(expenseCoreServiceAPI)
                    .addExpense(
                        backupCategory,
                        importV1Parameters,
                        categoryFinished,
                        backupExpense,
                        importV1SummaryGenerator,
                    )
            }
        }
    }


    private fun removeAllIfNecessary(importV1Parameters: ImportV1Parameters) {
        if (importV1Parameters.removeAllBeforeImport) {
            allExpensesRemover.removeAll()
        }
    }

}

fun validateIdIsUUID(id: String, lazyMessage: () -> String) {
    try {
        UUID.fromString(id)
    } catch (t: Throwable) {
        throw IllegalStateException(lazyMessage.invoke())
    }
}
