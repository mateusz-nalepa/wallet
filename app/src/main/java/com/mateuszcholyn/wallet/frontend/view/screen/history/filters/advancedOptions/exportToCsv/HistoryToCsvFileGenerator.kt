package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

data class HeaderNames(
    val categoryNameLabel: String,
    val paidAtLabel: String,
    val descriptionLabel: String,
    val amountLabel: String,
)

interface HistoryToCsvGenerator {
    fun generate(
        headerNames: HeaderNames,
        expensesList: List<SearchSingleResult>,
    ): String
}

class HistoryToCsvFileGenerator : HistoryToCsvGenerator {

    private val SEPARATOR = ";"

    override fun generate(
        headerNames: HeaderNames,
        expensesList: List<SearchSingleResult>,
    ): String =
        generateHeader(headerNames) + "\n" + expensesList.joinToString(separator = "\n") { generateRow(it) }


    private fun generateHeader(
        headerNames: HeaderNames,
    ): String =
        "${headerNames.categoryNameLabel}$SEPARATOR" +
            "${headerNames.amountLabel}$SEPARATOR" +
            "${headerNames.paidAtLabel}$SEPARATOR" +
            headerNames.descriptionLabel

    private fun generateRow(
        searchSingleResult: SearchSingleResult,
    ): String =
        "${searchSingleResult.categoryName.replaceSemicolonToPipe()}$SEPARATOR" +
            "${searchSingleResult.amount.asPrintableAmount()}$SEPARATOR" +
            "${searchSingleResult.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText()}$SEPARATOR" +
            searchSingleResult.description.replaceSemicolonToPipe()


    private fun String.replaceSemicolonToPipe(): String =
        this.replace(";", "|")
}