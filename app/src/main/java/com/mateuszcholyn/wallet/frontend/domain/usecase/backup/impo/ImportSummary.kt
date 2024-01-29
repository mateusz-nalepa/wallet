package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

data class ImportV1Summary(
    val numberOfCategories: Int,
    val numberOfExpenses: Int,

    val numberOfImportedCategories: Int,
    val numberOfSkippedCategories: Int,

    val numberOfImportedExpenses: Int,
    val numberOfSkippedExpenses: Int,
)

class ImportV1SummaryGenerator(
    val numberOfCategories: Int,
    val numberOfExpenses: Int,

    private var numberOfImportedCategories: Int = 0,
    private var numberOfSkippedCategories: Int = 0,

    private var numberOfImportedExpenses: Int = 0,
    private var numberOfSkippedExpenses: Int = 0,
) {

    companion object {
        fun from(backupWalletV1: BackupWalletV1): ImportV1SummaryGenerator =
            ImportV1SummaryGenerator(
                numberOfCategories = backupWalletV1.categories.size,
                numberOfExpenses = backupWalletV1.categories.flatMap { it.expenses }.size,
            )
    }

    fun markCategoryImported() {
        numberOfImportedCategories++
    }

    fun markCategorySkipped() {
        numberOfSkippedCategories++
    }

    fun markExpenseImported() {
        numberOfImportedExpenses++
    }

    fun markExpenseSkipped() {
        numberOfSkippedExpenses++
    }

    fun toImportV1Summary(): ImportV1Summary {
        assert(numberOfCategories == numberOfImportedCategories + numberOfSkippedCategories)
        assert(numberOfExpenses == numberOfImportedExpenses + numberOfSkippedExpenses)

        return ImportV1Summary(
            numberOfCategories = numberOfCategories,
            numberOfExpenses = numberOfExpenses,
            numberOfImportedCategories = numberOfImportedCategories,
            numberOfSkippedCategories = numberOfSkippedCategories,
            numberOfImportedExpenses = numberOfImportedExpenses,
            numberOfSkippedExpenses = numberOfSkippedExpenses,
        )
    }
}