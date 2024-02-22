package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun HistoryGroupingSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(modifier = Modifier.weight(4f)) {
            HistoryGroupingDropdownSectionStateless(
                historySearchForm = historySearchForm,
                historyScreenActions = historyScreenActions,
            )
        }

        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = historySearchForm.isGroupingEnabled,
                onCheckedChange = { newValue ->
                    historyScreenActions.onGroupingCheckboxChanged(newValue)
                })
            Text(text = stringResource(R.string.group))
        }
    }

}