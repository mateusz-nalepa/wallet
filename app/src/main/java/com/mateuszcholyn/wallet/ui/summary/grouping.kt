package com.mateuszcholyn.wallet.ui.summary

import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.scaffold.util.DropdownElement
import com.mateuszcholyn.wallet.util.toHumanDateText
import java.time.temporal.ChronoUnit

data class GroupElement(
        override val name: String,
        val groupFunctionName: (Expense) -> String,
        val groupFunction: (Expense) -> String,
) : DropdownElement

object GroupingData {

    private val groupingElements = listOf(
            GroupElement(
                    name = "wg. dnia",
                    groupFunctionName = { it.date.truncatedTo(ChronoUnit.DAYS).toHumanDateText() },
                    groupFunction = { it.date.truncatedTo(ChronoUnit.DAYS).toString() },
            ),
            GroupElement(
                    name = "wg. roku",
                    groupFunctionName = { it.date.year.toString() },
                    groupFunction = { it.date.year.toString() },
            ),
    )

    val groupingListBetter: List<GroupElement>
        get() = groupingElements
}

