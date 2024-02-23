package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmountWithoutDollar
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

data class CsvFileLabels(
    val categoryNameLabel: String,
    val amountLabel: String,
    val descriptionLabel: String,
    val paidAtLabel: String,
    val exportTitleLabel: String,
    val fileNamePrefix: String,
)

interface HistoryToCsvGenerator {
    fun generate(
        csvFileLabels: CsvFileLabels,
        expensesList: List<SearchSingleResult>,
    ): String
}

class HistoryToCsvFileGenerator : HistoryToCsvGenerator {

    private val SEPARATOR = ","

    override fun generate(
        csvFileLabels: CsvFileLabels,
        expensesList: List<SearchSingleResult>,
    ): String =
        generateHeader(csvFileLabels) + "\n" + expensesList.joinToString(separator = "\n") {
            generateRow(
                it
            )
        }


    private fun generateHeader(
        csvFileLabels: CsvFileLabels,
    ): String =
        "${csvFileLabels.categoryNameLabel.wrapInQuoteIfHasComa()}$SEPARATOR" +
            "${csvFileLabels.amountLabel.wrapInQuoteIfHasComa()}$SEPARATOR" +
            "${csvFileLabels.descriptionLabel.wrapInQuoteIfHasComa()}$SEPARATOR" +
            csvFileLabels.paidAtLabel.wrapInQuoteIfHasComa()

    private fun generateRow(
        searchSingleResult: SearchSingleResult,
    ): String =
        "${searchSingleResult.categoryName.wrapInQuoteIfHasComa()}$SEPARATOR" +
            "${searchSingleResult.amount.asPrintableAmountWithoutDollar()}$SEPARATOR" +
            "${searchSingleResult.description.wrapInQuoteIfHasComa()}$SEPARATOR" +
            searchSingleResult.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText()


    private fun String.wrapInQuoteIfHasComa(): String =
        if (this.contains(",")) {
            "\"$this\""
        } else {
            this
        }
}