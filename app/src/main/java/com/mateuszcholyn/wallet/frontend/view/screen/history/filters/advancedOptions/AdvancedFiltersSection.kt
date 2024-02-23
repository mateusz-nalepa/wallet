package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.history.ExportToCsvUiState
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.ButtonExportHistoryToCSV
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AdvancedFiltersSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
    exportUiState: ExportToCsvUiState,
) {
    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (historySearchForm.advancedOptionsVisible) {
                stringResource(R.string.historyScreen_showLess)
            } else {
                stringResource(R.string.historyScreen_showMore)
            }
        )
        Checkbox(checked = historySearchForm.advancedOptionsVisible, onCheckedChange = {
            historyScreenActions.onAdvancedFiltersVisible.invoke(!historySearchForm.advancedOptionsVisible)
        })
    }
    if (historySearchForm.advancedOptionsVisible) {
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
        ButtonExportHistoryToCSV(
            historyScreenActions = historyScreenActions,
            exportUiState = exportUiState,
        )
    }
}