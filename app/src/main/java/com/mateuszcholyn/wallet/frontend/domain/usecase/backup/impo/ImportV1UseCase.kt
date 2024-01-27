package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.SavedCategoryFromDb
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

data class ImportV1Parameters(
    val backupWalletV1: BackupWalletV1,
    val removeAllBeforeImport: Boolean,
)

// TODO: dodaj podsumowanie po imporcie XD
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
                .map { backupCategory -> getOrCreateCategoryById(backupCategory) }

        importV1Parameters
            .backupWalletV1
            .expenses
            .forEach { backupExpenseV1 -> addExpenseOrIgnoreIfExpenseIdIsPresent(savedCategoriesFromDb, backupExpenseV1) }

        println(importV1Parameters)
    }

    private fun getOrCreateCategoryById(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
    ): SavedCategoryFromDb {

        if (categoryCoreServiceAPI.getById(CategoryId(backupCategoryV1.id)) != null) {
            return SavedCategoryFromDb(
                categoryIdFromImportFile = CategoryId(backupCategoryV1.id),
                categoryIdFromDatabase = CategoryId(backupCategoryV1.id),
                // TODO: ta linia wygląda na mega niepotrzebną
                name = backupCategoryV1.name,
            )
        }

        val categoryFromDb =
            categoryCoreServiceAPI.add(
                CreateCategoryParameters(
                    categoryId = CategoryId(backupCategoryV1.id),
                    name = backupCategoryV1.name
                )
            )

        return SavedCategoryFromDb(
            categoryIdFromImportFile = CategoryId(backupCategoryV1.id),
            categoryIdFromDatabase = categoryFromDb.id,
            // TODO: ta linia wygląda na mega niepotrzebną
            name = backupCategoryV1.name,
        )
    }

    private fun addExpenseOrIgnoreIfExpenseIdIsPresent(
        savedCategoriesFromDb: List<SavedCategoryFromDb>,
        backupExpenseV1: BackupWalletV1.BackupExpenseV1,
    ) {
        if (expenseCoreServiceAPI.getById(ExpenseId(backupExpenseV1.expenseId)) != null) {
            return
        }

        AddExpenseParameters(
            expenseId = ExpenseId(backupExpenseV1.expenseId),
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
