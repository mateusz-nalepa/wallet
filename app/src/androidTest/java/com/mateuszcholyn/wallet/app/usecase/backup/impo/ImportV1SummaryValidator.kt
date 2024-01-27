package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

fun ImportV1Summary.validate(validationBlock: ImportV1SummaryValidator.() -> Unit) {
    ImportV1SummaryValidator(this)
        .validationBlock()
}

class ImportV1SummaryValidator(
    private val importV1Summary: ImportV1Summary,
) {

    fun numberOfInputDataMatch(backupWalletV1: BackupWalletV1) {
        numberOfCategoriesEqualTo(backupWalletV1.categories.size)
        numberOfExpensesEqualTo(backupWalletV1.expenses.size)
    }

    private fun numberOfCategoriesEqualTo(expectedNumberOfCategories: Int) {
        assert(importV1Summary.numberOfCategories == expectedNumberOfCategories) { "Number of categories does not match" }
    }

    private fun numberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(importV1Summary.numberOfExpenses == expectedNumberOfExpenses) { "Number of expenses does not match" }
    }

    fun numberOfImportedCategoriesEqualTo(expectedNumberOfImportedCategories: Int) {
        assert(importV1Summary.numberOfImportedCategories == expectedNumberOfImportedCategories) { "Imported number of categories does not match" }
    }

    fun numberOfSkippedCategoriesEqualTo(expectedNumberOfSkippedCategories: Int) {
        assert(importV1Summary.numberOfSkippedCategories == expectedNumberOfSkippedCategories) { "Skipped number of categories does not match" }
    }

    fun numberOfImportedExpensesEqualTo(expectedNumberOfImportedExpenses: Int) {
        assert(importV1Summary.numberOfImportedExpenses == expectedNumberOfImportedExpenses) { "Imported number of expenses does not match" }
    }

    fun numberOfSkippedExpensesEqualTo(expectedNumberOfSkippedExpenses: Int) {
        assert(importV1Summary.numberOfSkippedExpenses == expectedNumberOfSkippedExpenses) { "Skipped number of expenses does not match" }
    }

}