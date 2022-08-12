package com.mateuszcholyn.wallet.ui.screen.dummy

import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.newcode.app.backend.AllBackendServices
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.util.dateutils.toLocalDateTime


private data class BackupCategory(
    val id: Long,
    val name: String,
)

fun odpalMigracje(
    allBackendServices: AllBackendServices,
) {
    val objectMapper = ObjectMapper().findAndRegisterModules()

    allBackendServices.searchServiceAPI.removeAll()
    allBackendServices.categoriesQuickSummaryAPI.removeAll()
    allBackendServices.expenseCoreServiceAPI.removeAll()
    allBackendServices.categoryCoreServiceAPI.removeAll()

    val allExpenses = objectMapper.readValue(ALL_EXPENSES, BackupSaveModelAll::class.java)

    val categoriesWithBackups =
        allExpenses
            .expenses
            .map { BackupCategory(it.categoryId, it.categoryName) }
            .distinct()

    println(categoriesWithBackups)


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


    // new saved expenses
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