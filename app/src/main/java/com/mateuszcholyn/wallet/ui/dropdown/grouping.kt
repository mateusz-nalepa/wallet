package com.mateuszcholyn.wallet.ui.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.util.dateutils.toHumanDateText
import java.time.temporal.ChronoUnit

data class GroupElement(
        override val name: String,
        val groupFunctionName: (Expense) -> String,
        val groupFunction: (Expense) -> String,
) : DropdownElement

@Composable
fun groupingDataXD(): List<GroupElement> {
    return listOf(
            GroupElement(
                    name = stringResource(R.string.wgdnia),
                    groupFunctionName = { it.date.truncatedTo(ChronoUnit.DAYS).toHumanDateText() },
                    groupFunction = { it.date.truncatedTo(ChronoUnit.DAYS).toString() },
            ),
            GroupElement(
                    name = stringResource(R.string.wgroku),
                    groupFunctionName = { it.date.year.toString() },
                    groupFunction = { it.date.year.toString() },
            ),
    )
}

