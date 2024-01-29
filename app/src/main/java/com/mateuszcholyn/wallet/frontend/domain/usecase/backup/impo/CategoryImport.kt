package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import kotlinx.coroutines.CompletableDeferred

class CategoryImport(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) {

    suspend fun getOrCreateCategoryById(
        importV1Parameters: ImportV1Parameters,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): SavedCategoryFromDb {
        val nullableCategoryFromDatabaseByCategoryId =
            categoryCoreServiceAPI.getById(CategoryId(backupCategoryV1.id))
                ?: return run {
                    validateIdIsUUID(backupCategoryV1.id) {
                        "Invalid categoryId: [${backupCategoryV1.id}]. Have it been modified manually in file with backup data?"
                    }
                    addNewCategoryWhichHasBeenProbablyRemoved(
                        backupCategoryV1,
                        importV1SummaryGenerator
                    )
                }

        return if (categoryChangedAfterExport(
                backupCategoryV1,
                nullableCategoryFromDatabaseByCategoryId,
            )
        ) {
            askUserWhatToDoWhenCategoryNameChanged(
                nullableCategoryFromDatabaseByCategoryId,
                backupCategoryV1,
                importV1SummaryGenerator,
                importV1Parameters
            )
        } else {
            useExistingCategoryResult(backupCategoryV1, importV1SummaryGenerator)
        }
    }

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

        return SavedCategoryFromDb(categoryId = addedCategory.id)
            .also { importV1SummaryGenerator.markCategoryImported() }
    }

    private fun useExistingCategoryResult(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): SavedCategoryFromDb =
        SavedCategoryFromDb(categoryId = CategoryId(backupCategoryV1.id))
            .also { importV1SummaryGenerator.markCategorySkipped() }


    private fun categoryChangedAfterExport(
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        categoryFromDb: CategoryV2,
    ): Boolean =
        categoryFromDb.name != backupCategoryV1.name

    private suspend fun askUserWhatToDoWhenCategoryNameChanged(
        existingCategoryFromDatabaseByCategoryId: CategoryV2,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
        importV1Parameters: ImportV1Parameters,
    ): SavedCategoryFromDb {
        val deferred = CompletableDeferred<() -> SavedCategoryFromDb>()

        importV1Parameters
            .onCategoryNameChangedAction
            .invoke(
                OnCategoryChangedInput(
                    keepCategoryFromDatabase = {
                        deferred.complete {
                            useExistingCategoryResult(
                                backupCategoryV1,
                                importV1SummaryGenerator
                            )
                        }
                    },
                    useCategoryFromBackup = {
                        deferred.complete {
                            updateCategoryByUsingDataFromBackup(
                                existingCategoryFromDatabaseByCategoryId,
                                backupCategoryV1,
                                importV1SummaryGenerator,
                            )
                        }
                    },
                )
            )
        return deferred.await().invoke()
    }

    private fun updateCategoryByUsingDataFromBackup(
        existingCategoryFromDatabaseByCategoryId: CategoryV2,
        backupCategoryV1: BackupWalletV1.BackupCategoryV1,
        importV1SummaryGenerator: ImportV1SummaryGenerator,
    ): SavedCategoryFromDb {

        val updatedCategory =
            existingCategoryFromDatabaseByCategoryId
                .copy(name = backupCategoryV1.name)
                .let { categoryCoreServiceAPI.update(it) }

        return SavedCategoryFromDb(categoryId = updatedCategory.id)
            .also { importV1SummaryGenerator.markCategoryImported() }
    }

}
