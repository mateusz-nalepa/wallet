package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

fun assertExpenseFromImportFileEqualToExpenseFromDbIgnoringExpenseId(
    givenRandomBackupExpenseV1: BackupWalletV1.BackupExpenseV1,
    expenseFromDb: ExpenseV2
) {
    assert(givenRandomBackupExpenseV1.amount == expenseFromDb.amount) { "amount not equal" }
    assert(givenRandomBackupExpenseV1.description == expenseFromDb.description) { "description not equal" }
    assert(givenRandomBackupExpenseV1.paidAt == InstantConverter.toLong(expenseFromDb.paidAt)) { "paidAt not equal" }
    assert(givenRandomBackupExpenseV1.categoryId == expenseFromDb.categoryId.id) { "categoryId not equal" }
}
