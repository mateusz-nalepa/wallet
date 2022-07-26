package com.mateuszcholyn.wallet.ui.screen.summary.advancedFilters

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
import com.mateuszcholyn.wallet.ui.screen.summary.SummaryViewModel
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun SummaryGroupingSection(
    summaryViewModel: SummaryViewModel = hiltViewModel(),
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
                checked = summaryViewModel.summarySearchForm.isGroupingEnabled,
                onCheckedChange = { newValue ->
                    summaryViewModel.groupingCheckBoxChecked(newValue)
                })
            Text(text = stringResource(R.string.group))
        }
    }

}