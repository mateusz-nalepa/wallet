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

    var numberOfImportedCategories: Int = 0,
    var numberOfSkippedCategories: Int = 0,

    var numberOfImportedExpenses: Int = 0,
    var numberOfSkippedExpenses: Int = 0,
) {
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