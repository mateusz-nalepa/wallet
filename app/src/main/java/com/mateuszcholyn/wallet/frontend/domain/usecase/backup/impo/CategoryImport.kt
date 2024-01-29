package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
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

    private fun addNewCategoryWhichHasBeenProbablyRemoved(): SavedCategoryFromDb {

        validateIdIsUUID(backupCategoryV1.id) {
            "Invalid categoryId: [${backupCategoryV1.id}]. Have it been modified manually in file with backup data?"
        }

        return categoryCoreServiceAPI
            .add(
                CreateCategoryParameters(
                    categoryId = CategoryId(backupCategoryV1.id),
                    name = backupCategoryV1.name
                )
            )
            .let { SavedCategoryFromDb(categoryId = it.id) }
            .also { importV1SummaryGenerator.markCategoryImported() }
    }

    private fun useExistingCategoryResult(): SavedCategoryFromDb =
        SavedCategoryFromDb(CategoryId(backupCategoryV1.id))
            .also { importV1SummaryGenerator.markCategorySkipped() }

    private fun categoryChangedAfterExport(
        categoryFromDb: CategoryV2,
    ): Boolean =
        categoryFromDb.name != backupCategoryV1.name

    private suspend fun askUserWhatToDoWhenCategoryNameChanged(
        categoryFromDb: CategoryV2,
    ): SavedCategoryFromDb {
        val deferred = CompletableDeferred<() -> SavedCategoryFromDb>()

        importV1Parameters
            .onCategoryNameChangedAction
            .invoke(
                OnCategoryChangedInput(
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

    private fun updateCategoryByUsingDataFromBackup(
        categoryFromDb: CategoryV2,
    ): SavedCategoryFromDb =
        categoryFromDb
            .copy(name = backupCategoryV1.name)
            .let { categoryCoreServiceAPI.update(it) }
            .let { SavedCategoryFromDb(it.id) }
            .also { importV1SummaryGenerator.markCategoryImported() }

}
