package com.mateuszcholyn.wallet.app.usecase.backup

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1


fun assertExportedCategoryEqualToCategoryFromDb(
    exportedExpense: BackupWalletV1.BackupCategoryV1,
    categoryV2: CategoryV2,
) {
    assert(exportedExpense.id == categoryV2.id.id) { "categoryId not equal" }
    assert(exportedExpense.name == categoryV2.name) { "categoryName not equal" }
}

fun assertExportedExpenseEqualToExpenseFromDb(
    exportedExpense: BackupWalletV1.BackupExpenseV1,
    expenseFromDb: ExpenseV2,
) {
    assertExpenseFromImportFileEqualToExpenseFromDb(
        exportedExpense,
        expenseFromDb,
    )
}


fun assertExpenseFromImportFileEqualToExpenseFromDb(
    givenRandomBackupExpenseV1: BackupWalletV1.BackupExpenseV1,
    expenseFromDb: ExpenseV2,
) {
    assert(givenRandomBackupExpenseV1.expenseId == expenseFromDb.expenseId.id) { "expenseId not equal" }
    assert(givenRandomBackupExpenseV1.amount == expenseFromDb.amount) { "amount not equal" }
    assert(givenRandomBackupExpenseV1.description == expenseFromDb.description) { "description not equal" }
    assert(givenRandomBackupExpenseV1.paidAt == InstantConverter.toLong(expenseFromDb.paidAt)) { "paidAt not equal" }
    assert(givenRandomBackupExpenseV1.categoryId == expenseFromDb.categoryId.id) { "categoryId not equal" }
}
