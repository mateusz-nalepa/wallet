package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SavedCategoryFromDb
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

data class ImportV1Parameters(
    val backupWalletV1: BackupWalletV1,
    val removeAllBeforeImport: Boolean,
)

class ImportV1UseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val allExpensesRemover: AllExpensesRemover,
) : UseCase {

    fun invoke(importV1Parameters: ImportV1Parameters) {
        removeAllIfNecessary(importV1Parameters)

        val categoriesFromBackup = importV1Parameters.backupWalletV1.categories

        val savedCategoriesFromDb =
            categoriesFromBackup
                .map { backupCategory -> getOrCreateCategoryByName(backupCategory) }

        importV1Parameters
            .backupWalletV1
            .expenses
            .forEach { backupExpenseV1 -> addExpense(savedCategoriesFromDb, backupExpenseV1) }

        println(importV1Parameters)
    }

    private fun getOrCreateCategoryByName(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
    ): SavedCategoryFromDb {

        val categoryFromDb =
            categoryCoreServiceAPI.getByCategoryName(backupCategoryV1.name)
                ?: categoryCoreServiceAPI.add(CreateCategoryParameters(name = backupCategoryV1.name))

        return SavedCategoryFromDb(
            categoryIdFromImportFile = CategoryId(backupCategoryV1.id),
            categoryIdFromDatabase = categoryFromDb.id,
            // TODO: ta linia wygląda na mega niepotrzebną
            name = backupCategoryV1.name,
        )
    }

    private fun addExpense(
        savedCategoriesFromDb: List<SavedCategoryFromDb>,
        backupExpenseV1: BackupWalletV1.BackupExpenseV1,
    ) {

        AddExpenseParameters(
            amount = backupExpenseV1.amount,
            description = backupExpenseV1.description,
            paidAt = InstantConverter.toInstant(backupExpenseV1.paidAt),
            categoryId = savedCategoriesFromDb
                .first { it.categoryIdFromImportFile.id == backupExpenseV1.categoryId }.categoryIdFromDatabase,
        )
            .let { expenseCoreServiceAPI.add(it) }
    }

    private fun removeAllIfNecessary(importV1Parameters: ImportV1Parameters) {
        if (importV1Parameters.removeAllBeforeImport) {
            allExpensesRemover.removeAll()
        }
    }

}
