package com.mateuszcholyn.wallet.ui.summary

import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.util.toHumanDateText
import java.time.temporal.ChronoUnit

data class GroupElement(
        val groupType: String,
        val groupFunctionName: (Expense) -> String,
        val groupFunction: (Expense) -> String,
)

object GroupingData {

    private val groupingElements = listOf(
            GroupElement(
                    groupType = "wg. dnia",
                    groupFunctionName = { it.date.truncatedTo(ChronoUnit.DAYS).toHumanDateText() },
                    groupFunction = { it.date.truncatedTo(ChronoUnit.DAYS).toString() },
            ),
            GroupElement(
                    groupType = "wg. kategorii",
                    groupFunctionName = { it.category.name },
                    groupFunction = { it.category.name },
            ),
            GroupElement(
                    groupType = "wg. roku",
                    groupFunctionName = { it.date.year.toString() },
                    groupFunction = { it.date.year.toString() },
            ),
    )

    val groupingListBetter: List<GroupElement>
        get() = groupingElements
}

