package com.mateuszcholyn.wallet.frontend.infrastructure.backup.read

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.frontend.view.screen.backup.SaveAllExpensesV2WithCategoriesV2Model


private data class BackupCategory(
    val id: String,
    val name: String,
)

fun readBackupData(
    allBackendServices: AllBackendServices,
) {
    val objectMapper = ObjectMapper().findAndRegisterModules()

    allBackendServices.searchServiceAPI.removeAll()
    allBackendServices.categoriesQuickSummaryAPI.removeAll()
    allBackendServices.expenseCoreServiceAPI.removeAll()
    allBackendServices.categoryCoreServiceAPI.removeAll()

    val allExpenses = objectMapper.readValue(
        ALL_EXPENSES_AS_JSON,
        SaveAllExpensesV2WithCategoriesV2Model::class.java
    )

    val categoriesWithBackups =
        allExpenses
            .categories
            .map { BackupCategory(it.id.id, it.name) }
            .distinct()


    val newSavedCategories =
        categoriesWithBackups
            .map { backupCategory ->
                val addedCategory =
                    allBackendServices.categoryCoreServiceAPI.add(
                        CreateCategoryParameters(name = backupCategory.name)
                    )

                SavedCategoryFromDb(
                    categoryIdFromImportFile = CategoryId(backupCategory.id),
                    categoryIdFromDatabase = addedCategory.id,
                    name = backupCategory.name,
                )
            }


    allExpenses
        .expenses
        .forEach { backupSaveModel ->

            allBackendServices
                .expenseCoreServiceAPI
                .add(
                    AddExpenseParameters(
                        amount = backupSaveModel.amount,
                        description = backupSaveModel.description,
                        paidAt = backupSaveModel.paidAt,
                        categoryId = newSavedCategories.first { it.categoryIdFromImportFile == backupSaveModel.categoryId }.categoryIdFromDatabase,
                    )
                )
        }

}


data class SavedCategoryFromDb(
    val categoryIdFromImportFile: CategoryId,
    val categoryIdFromDatabase: CategoryId,
    val name: String,
)