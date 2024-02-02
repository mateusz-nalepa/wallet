package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

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

    private var onImportProgress: (ImportV1Summary) -> Unit,
) {

    companion object {
        fun from(importV1Parameters: ImportV1Parameters): ImportV1SummaryGenerator =
            ImportV1SummaryGenerator(
                numberOfCategories = importV1Parameters.backupWalletV1.categories.size,
                numberOfExpenses = importV1Parameters.backupWalletV1.categories.flatMap { it.expenses }.size,
                onImportProgress = importV1Parameters.onImportProgress,
            )
    }

    fun markCategoryImported() {
        numberOfImportedCategories++
        onImportProgress.invoke(toImportV1SummaryInProgress())
    }

    fun markCategorySkipped() {
        numberOfSkippedCategories++
        onImportProgress.invoke(toImportV1SummaryInProgress())
    }

    fun markExpenseImported() {
        numberOfImportedExpenses++
        onImportProgress.invoke(toImportV1SummaryInProgress())
    }

    fun markExpenseSkipped() {
        numberOfSkippedExpenses++
        onImportProgress.invoke(toImportV1SummaryInProgress())
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

    private fun toImportV1SummaryInProgress(): ImportV1Summary {
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