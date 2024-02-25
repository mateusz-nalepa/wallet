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
        generateHeader(csvFileLabels) + "\n" +
            expensesList.joinToString(separator = "\n") { generateRow(it) }


    private fun generateHeader(
        csvFileLabels: CsvFileLabels,
    ): String =
        "${csvFileLabels.categoryNameLabel.prepareToCsv()}$SEPARATOR" +
            "${csvFileLabels.amountLabel.prepareToCsv()}$SEPARATOR" +
            "${csvFileLabels.descriptionLabel.prepareToCsv()}$SEPARATOR" +
            csvFileLabels.paidAtLabel.prepareToCsv()

    private fun generateRow(
        searchSingleResult: SearchSingleResult,
    ): String =
        "${searchSingleResult.categoryName.prepareToCsv()}$SEPARATOR" +
            "${
                searchSingleResult.amount
                    .asPrintableAmountWithoutDollar()
                    .prepareToCsv()
            }$SEPARATOR" +
            "${searchSingleResult.description.prepareToCsv()}$SEPARATOR" +
            searchSingleResult.paidAt
                .fromUTCInstantToUserLocalTimeZone()
                .toHumanDateTimeText()
                .prepareToCsv()


    private fun String.prepareToCsv(): String =
        wrapInQuoteIfHasComa()
            .replaceNewLineWithString()

    private fun String.wrapInQuoteIfHasComa(): String =
        if (this.contains(",")) {
            "\"$this\""
        } else {
            this
        }

    private fun String.replaceNewLineWithString(): String =
        if (this.contains("\n")) {
            this.replace("\n", " ")
        } else {
            this
        }
}