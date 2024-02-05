package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun SummaryGroupingSectionStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(modifier = Modifier.weight(4f)) {
            SummaryGroupingDropdownSectionStateless(
                summarySearchForm = summarySearchForm,
                summaryScreenActions = summaryScreenActions,
            )
        }

        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = summarySearchForm.isGroupingEnabled,
                onCheckedChange = { newValue ->
                    summaryScreenActions.onGroupingCheckboxChanged(newValue)
                })
            Text(text = stringResource(R.string.group))
        }
    }

}