package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupCategoryV1
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupExpenseV1
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
        categories: List<Category>,
        expenses: List<Expense>,
    ): BackupWalletV1 =
        BackupWalletV1(
            categories = categories
                .map { category ->
                    BackupCategoryV1(
                        id = category.id.id,
                        name = category.name,
                        expenses = expenses
                            .filter { it.categoryId == category.id }
                            .map {
                                BackupExpenseV1(
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
