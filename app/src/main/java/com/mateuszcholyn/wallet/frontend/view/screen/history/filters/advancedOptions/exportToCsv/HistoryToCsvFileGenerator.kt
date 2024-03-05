package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.BigDecimalAsFormattedAmountFunction
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

data class CsvGeneratorParameters(
    val categoryNameLabel: String,
    val amountLabel: String,
    val descriptionLabel: String,
    val paidAtLabel: String,
    val exportTitleLabel: String,
    val fileNamePrefix: String,
    val bigDecimalAsFormattedAmountFunction: BigDecimalAsFormattedAmountFunction,
)

interface HistoryToCsvGenerator {
    fun generate(
        csvGeneratorParameters: CsvGeneratorParameters,
        expensesList: List<SearchSingleResult>,
    ): String
}

class HistoryToCsvFileGenerator : HistoryToCsvGenerator {

    private val SEPARATOR = ","

    override fun generate(
        csvGeneratorParameters: CsvGeneratorParameters,
        expensesList: List<SearchSingleResult>,
    ): String =
        generateHeader(csvGeneratorParameters) + "\n" +
            expensesList
                .joinToString(separator = "\n") {
                    generateRow(csvGeneratorParameters, it)
                }


    private fun generateHeader(
        csvGeneratorParameters: CsvGeneratorParameters,
    ): String =
        "${csvGeneratorParameters.categoryNameLabel.prepareToCsv()}$SEPARATOR" +
            "${csvGeneratorParameters.amountLabel.prepareToCsv()}$SEPARATOR" +
            "${csvGeneratorParameters.descriptionLabel.prepareToCsv()}$SEPARATOR" +
            csvGeneratorParameters.paidAtLabel.prepareToCsv()

    private fun generateRow(
        csvGeneratorParameters: CsvGeneratorParameters,
        searchSingleResult: SearchSingleResult,
    ): String =
        "${searchSingleResult.categoryName.prepareToCsv()}$SEPARATOR" +
            "${
                csvGeneratorParameters.bigDecimalAsFormattedAmountFunction.invoke(searchSingleResult.amount)
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