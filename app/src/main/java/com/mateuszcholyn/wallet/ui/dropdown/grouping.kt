package com.mateuszcholyn.wallet.ui.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.util.dateutils.toHumanDateText
import java.time.temporal.ChronoUnit

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
            name = "R.string.wgroku",
            nameKey = R.string.wgroku,
            groupFunctionName = { it.paidAt.year.toString() },
            groupFunction = { it.paidAt.year.toString() },
        ),
    )
}

