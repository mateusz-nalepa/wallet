package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun SummaryGroupingSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(modifier = Modifier.weight(4f)) {
            SummaryGroupingDropdownSection()
        }

        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = summaryScreenViewModel.summarySearchForm.isGroupingEnabled,
                onCheckedChange = { newValue ->
                    summaryScreenViewModel.groupingCheckBoxChecked(newValue)
                })
            Text(text = stringResource(R.string.group))
        }
    }

}