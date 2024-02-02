package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.delay
import java.util.UUID


class ImportV1UseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val allExpensesRemover: AllExpensesRemover,
    private val transactionManager: TransactionManager,
) : UseCase {

    suspend fun invoke(importV1Parameters: ImportV1Parameters): ImportV1Summary =
        transactionManager.runInTransaction {
            unsafeImportAllData(importV1Parameters)
        }

    private suspend fun unsafeImportAllData(
        importV1Parameters: ImportV1Parameters,
    ): ImportV1Summary {
        removeAllIfNecessary(importV1Parameters)

        return ImportV1SummaryGenerator
            .from(importV1Parameters)
            .also { importBackup(importV1Parameters, it) }
            .toImportV1Summary()
    }

    private suspend fun removeAllIfNecessary(importV1Parameters: ImportV1Parameters) {
        if (importV1Parameters.removeAllBeforeImport) {
            allExpensesRemover.removeAll()
        }
    }

    private suspend fun importBackup(
        importV1Parameters: ImportV1Parameters,
        summaryGenerator: ImportV1SummaryGenerator,
    ) {
        importV1Parameters
            .backupWalletV1
            .categories
            .map { backupCategory ->
                importSingleCategory(backupCategory, importV1Parameters, summaryGenerator)
            }
    }

    private suspend fun importSingleCategory(
        backupCategory: BackupWalletV1.BackupCategoryV1,
        importV1Parameters: ImportV1Parameters,
        summaryGenerator: ImportV1SummaryGenerator,
    ) {
        delay(1)
        val savedCategoryFromDb =
            CategoryImport(
                categoryCoreServiceAPI,
                summaryGenerator,
                importV1Parameters,
                backupCategory,
            ).getOrCreateCategory()

        backupCategory
            .expenses
            .forEach { backupExpense ->
                delay(2)
                ExpenseImport(
                    expenseCoreServiceAPI,
                    backupCategory,
                    importV1Parameters,
                    savedCategoryFromDb,
                    backupExpense,
                    summaryGenerator,
                ).addExpense()
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

data class SavedCategoryFromDb(
    val categoryId: CategoryId,
)
