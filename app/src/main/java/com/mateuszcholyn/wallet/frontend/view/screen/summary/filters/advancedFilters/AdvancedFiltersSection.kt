package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AdvancedFiltersSectionStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    var advancedFiltersExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (advancedFiltersExpanded) {
                stringResource(R.string.hideFilters)
            } else {
                stringResource(R.string.showFilters)
            }
        )
        Checkbox(checked = advancedFiltersExpanded, onCheckedChange = {
            advancedFiltersExpanded = !advancedFiltersExpanded
        })
    }
    if (advancedFiltersExpanded) {
        SummarySortingSectionStateless(
            summarySearchForm = summarySearchForm,
            summaryScreenActions = summaryScreenActions,
        )
        SummaryGroupingSectionStateless(
            summarySearchForm = summarySearchForm,
            summaryScreenActions = summaryScreenActions,
        )
        AmountRangeSectionStateless(
            summarySearchForm = summarySearchForm,
            summaryScreenActions = summaryScreenActions,
        )
    }
}