package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import android.util.Log
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.CompletableDeferred

class CategoryImport(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val importV1SummaryGenerator: ImportV1SummaryGenerator,
    private val importV1Parameters: ImportV1Parameters,
    private val backupCategoryV1: BackupWalletV1.BackupCategoryV1
) {

    suspend fun getOrCreateCategory(): SavedCategoryFromDb {
        Log.d("XD", "category")

        val categoryFromDb =
            categoryCoreServiceAPI
                .getById(CategoryId(backupCategoryV1.id))
                ?: return addNewCategoryWhichHasBeenProbablyRemoved()

        return if (categoryChangedAfterExport(categoryFromDb)) {
            askUserWhatToDoWhenCategoryNameChanged(categoryFromDb)
        } else {
            useExistingCategoryResult()
        }
    }

    private suspend fun addNewCategoryWhichHasBeenProbablyRemoved(): SavedCategoryFromDb =
        categoryCoreServiceAPI
            .add(
                CreateCategoryParameters(
                    categoryId = CategoryId(backupCategoryV1.id),
                    name = backupCategoryV1.name
                )
            )
            .let { SavedCategoryFromDb(categoryId = it.id) }
            .also { importV1SummaryGenerator.markCategoryImported() }

    private fun useExistingCategoryResult(): SavedCategoryFromDb =
        SavedCategoryFromDb(CategoryId(backupCategoryV1.id))
            .also { importV1SummaryGenerator.markCategorySkipped() }

    private fun categoryChangedAfterExport(
        categoryFromDb: Category,
    ): Boolean =
        categoryFromDb.name != backupCategoryV1.name

    private suspend fun askUserWhatToDoWhenCategoryNameChanged(
        categoryFromDb: Category,
    ): SavedCategoryFromDb {
        val deferred = CompletableDeferred<suspend () -> SavedCategoryFromDb>()

        importV1Parameters
            .onCategoryNameChangedAction
            .invoke(
                OnCategoryChangedInput(
                    categoriesToCompare = CategoriesToCompare(
                        categoryFromBackup = ComparableCategory(
                            categoryName = backupCategoryV1.name,
                        ),
                        categoryFromDatabase = ComparableCategory(
                            categoryName = categoryFromDb.name,
                        )
                    ),
                    keepCategoryFromDatabase = {
                        deferred.complete {
                            useExistingCategoryResult()
                        }
                    },
                    useCategoryFromBackup = {
                        deferred.complete {
                            updateCategoryByUsingDataFromBackup(categoryFromDb)
                        }
                    },
                )
            )
        return deferred.await().invoke()
    }

    private suspend fun updateCategoryByUsingDataFromBackup(
        categoryFromDb: Category,
    ): SavedCategoryFromDb =
        categoryFromDb
            .copy(name = backupCategoryV1.name)
            .let { categoryCoreServiceAPI.update(it) }
            .let { SavedCategoryFromDb(it.id) }
            .also { importV1SummaryGenerator.markCategoryImported() }

}


data class CategoriesToCompare(
    val categoryFromBackup: ComparableCategory,
    val categoryFromDatabase: ComparableCategory,
)

data class ComparableCategory(
    val categoryName: String,
)
