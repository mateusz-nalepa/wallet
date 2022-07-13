package com.mateuszcholyn.wallet.ui.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.util.dateutils.toHumanDateText
import java.time.temporal.ChronoUnit

data class GroupElement(
    override val name: String,
    override val nameKey: Int? = null,
    val groupFunctionName: (Expense) -> String,
    val groupFunction: (Expense) -> String,
) : DropdownElement

fun groupingDataXD(): List<GroupElement> {
    return listOf(
        GroupElement(
            name = "R.string.wgdnia",
            nameKey = R.string.wgdnia,
            groupFunctionName = { it.date.truncatedTo(ChronoUnit.DAYS).toHumanDateText() },
            groupFunction = { it.date.truncatedTo(ChronoUnit.DAYS).toString() },
        ),
        GroupElement(
            name = "R.string.wgroku",
            nameKey = R.string.wgroku,
            groupFunctionName = { it.date.year.toString() },
            groupFunction = { it.date.year.toString() },
        ),
    )
}

