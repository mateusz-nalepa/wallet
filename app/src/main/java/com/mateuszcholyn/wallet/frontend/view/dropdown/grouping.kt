package com.mateuszcholyn.wallet.frontend.view.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateText
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanMonthAndYear
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

data class GroupElement(
    override val name: String,
    override val nameKey: Int? = null,
    val groupFunctionName: (SearchSingleResult) -> String,
    val groupFunction: (SearchSingleResult) -> String,
) : DropdownElement

fun groupingDataXD(): List<GroupElement> {
    return listOf(
        GroupElement(
            name = "R.string.wgdnia",
            nameKey = R.string.wgdnia,
            groupFunctionName = { it.paidAt.truncatedTo(ChronoUnit.DAYS).toHumanDateText() },
            groupFunction = { it.paidAt.truncatedTo(ChronoUnit.DAYS).toString() },
        ),
        GroupElement(
            name = "R.string.wgmiesiaca",
            nameKey = R.string.wgmiesiaca,
            groupFunctionName = { it.paidAt.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS).toHumanMonthAndYear() },
            groupFunction = { it.paidAt.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS).toString() },
        ),
        GroupElement(
            name = "R.string.wgroku",
            nameKey = R.string.wgroku,
            groupFunctionName = { it.paidAt.year.toString() },
            groupFunction = { it.paidAt.year.toString() },
        ),
    )
}

