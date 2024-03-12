package com.mateuszcholyn.wallet.frontend.view.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateText
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanMonthAndYear
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

data class GroupElement(
    override val name: String? = null,
    override val subName: String? = null,
    override val nameKey: Int,
    val groupFunctionName: (SearchSingleResult) -> String,
    val groupFunction: (SearchSingleResult) -> String,
) : DropdownElement {
    companion object {
        val default =
            GroupElement(
                nameKey = R.string.group_byDay,
                groupFunctionName = {
                    it.paidAt.fromUTCInstantToUserLocalTimeZone().truncatedTo(ChronoUnit.DAYS)
                        .toHumanDateText()
                },
                groupFunction = {
                    it.paidAt.fromUTCInstantToUserLocalTimeZone().truncatedTo(ChronoUnit.DAYS)
                        .toString()
                },
            )
    }
}

fun groupingElements(): List<GroupElement> {
    return listOf(
        GroupElement.default,
        GroupElement(
            nameKey = R.string.group_byMonth,
            groupFunctionName = {
                it.paidAt.fromUTCInstantToUserLocalTimeZone()
                    .with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS)
                    .toHumanMonthAndYear()
            },
            groupFunction = {
                it.paidAt.fromUTCInstantToUserLocalTimeZone()
                    .with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS)
                    .toString()
            },
        ),
        GroupElement(
            nameKey = R.string.group_byYear,
            groupFunctionName = { it.paidAt.fromUTCInstantToUserLocalTimeZone().year.toString() },
            groupFunction = { it.paidAt.fromUTCInstantToUserLocalTimeZone().year.toString() },
        ),
    )
}

