package com.mateuszcholyn.wallet.frontend.view.screen.summary.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AdvancedFiltersSection() {
    var advancedFiltersExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =
            if (advancedFiltersExpanded)
                stringResource(R.string.hideFilters)
            else
                stringResource(R.string.showFilters),
        )
        Checkbox(checked = advancedFiltersExpanded, onCheckedChange = {
            advancedFiltersExpanded = !advancedFiltersExpanded
        })
    }
    if (advancedFiltersExpanded) {
        SummarySortingSection()
        SummaryGroupingSection()
        AmountRangeSection()
    }
}