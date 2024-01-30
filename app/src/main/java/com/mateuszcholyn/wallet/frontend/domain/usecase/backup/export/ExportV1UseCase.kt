package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

class ExportV1UseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val transactionManager: TransactionManager,
) : UseCase {

    suspend fun invoke(): BackupWalletV1 =
        transactionManager.runInTransaction {
            BackupV1Creator.createBackupWalletV1(
                categories = categoryCoreServiceAPI.getAll(),
                expenses = expenseCoreServiceAPI.getAll(),
            )
        }

}

object BackupV1Creator {

    fun createBackupWalletV1(
        categories: List<CategoryV2>,
        expenses: List<ExpenseV2>,
    ): BackupWalletV1 =
        BackupWalletV1(
            version = 1,
            categories = categories
                .map { category ->
                    BackupWalletV1.BackupCategoryV1(
                        id = category.id.id,
                        name = category.name,
                        expenses = expenses
                            .filter { it.categoryId == category.id }
                            .map {
                                BackupWalletV1.BackupCategoryV1.BackupExpenseV1(
                                    expenseId = it.expenseId.id,
                                    amount = it.amount,
                                    description = it.description,
                                    paidAt = it.paidAt.toEpochMilli(),
                                )

                            }
                    )
                },
        )
}
