package com.mateuszcholyn.wallet.ui.summary

import com.mateuszcholyn.wallet.domain.expense.Expense
import java.time.temporal.ChronoUnit

data class GroupElement(
        val name: String,
        val groupFunction: (Expense) -> String,
)

object GroupingData {

    private val groupingElements = listOf(
            GroupElement(
                    name = "wg. kategorii",
                    groupFunction = { it.category.name },
            ),
            GroupElement(
                    name = "wg. dnia",
                    groupFunction = { it.date.truncatedTo(ChronoUnit.DAYS).toString() },
            ),
            GroupElement(
                    name = "wg. roku",
                    groupFunction = { it.date.year.toString() },
            ),
    )

    val groupingListBetter: List<GroupElement>
        get() = groupingElements

}
