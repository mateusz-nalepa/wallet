package com.mateuszcholyn.wallet.frontend.infrastructure.backup.read

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toLocalDateTime


private data class BackupCategory(
    val id: Long,
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

    val allExpenses = objectMapper.readValue(ALL_EXPENSES_AS_JSON, BackupSaveModelAll::class.java)

    val categoriesWithBackups =
        allExpenses
            .expenses
            .map { BackupCategory(it.categoryId, it.categoryName) }
            .distinct()


    val newSavedCategories =
        categoriesWithBackups
            .map { backupCategory ->
                val addedCategory =
                    allBackendServices.categoryCoreServiceAPI.add(
                        CreateCategoryParameters(name = backupCategory.name)
                    )


                SavedCategoryFromDb(
                    oldCategoryId = backupCategory.id,
                    newCategoryId = addedCategory.id,
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
                        paidAt = backupSaveModel.date.toLocalDateTime(),
                        categoryId = newSavedCategories.first { it.oldCategoryId == backupSaveModel.categoryId }.newCategoryId,
                    )
                )
        }

}


data class SavedCategoryFromDb(
    val oldCategoryId: Long,
    val newCategoryId: CategoryId,
    val name: String,
)