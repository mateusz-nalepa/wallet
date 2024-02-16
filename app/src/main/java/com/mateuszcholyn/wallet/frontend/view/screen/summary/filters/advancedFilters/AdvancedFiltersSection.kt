package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (summarySearchForm.advancedFiltersVisible) {
                stringResource(R.string.hideFilters)
            } else {
                stringResource(R.string.showFilters)
            }
        )
        Checkbox(checked = summarySearchForm.advancedFiltersVisible, onCheckedChange = {
            summaryScreenActions.onAdvancedFiltersVisible.invoke(!summarySearchForm.advancedFiltersVisible)
        })
    }
    if (summarySearchForm.advancedFiltersVisible) {
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