package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AdvancedFiltersSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (historySearchForm.advancedFiltersVisible) {
                stringResource(R.string.hideFilters)
            } else {
                stringResource(R.string.showFilters)
            }
        )
        Checkbox(checked = historySearchForm.advancedFiltersVisible, onCheckedChange = {
            historyScreenActions.onAdvancedFiltersVisible.invoke(!historySearchForm.advancedFiltersVisible)
        })
    }
    if (historySearchForm.advancedFiltersVisible) {
        HistorySortingSectionStateless(
            historySearchForm = historySearchForm,
            historyScreenActions = historyScreenActions,
        )
        HistoryGroupingSectionStateless(
            historySearchForm = historySearchForm,
            historyScreenActions = historyScreenActions,
        )
        AmountRangeSectionStateless(
            historySearchForm = historySearchForm,
            historyScreenActions = historyScreenActions,
        )
    }
}