package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

class ExportV1UseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(): BackupWalletV1 =
        BackupV1Creator.createBackupWalletV1(
            categories = categoryCoreServiceAPI.getAll(),
            expenses = expenseCoreServiceAPI.getAll(),
        )

}

object BackupV1Creator {

    fun createBackupWalletV1(
        categories: List<CategoryV2>,
        expenses: List<ExpenseV2>,
    ): BackupWalletV1 =
        BackupWalletV1(
            version = 1,
            categories = categories
                .map {
                    BackupWalletV1.BackupCategoryV1(
                        id = it.id.id,
                        name = it.name,
                    )
                },
            expenses = expenses.map {
                BackupWalletV1.BackupExpenseV1(
                    expenseId = it.expenseId.id,
                    amount = it.amount,
                    description = it.description,
                    paidAt = it.paidAt.toEpochMilli(),
                    categoryId = it.categoryId.id,
                )
            }
        )
}
