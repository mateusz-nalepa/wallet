package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.history.ExportToCsvUiState
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier

@Composable
fun ButtonExportHistoryToCSV(
    historyScreenActions: HistoryScreenActions,
    exportUiState: ExportToCsvUiState,
) {
    Button(
        onClick = {
            historyScreenActions.onExportHistory.invoke()
        },
        modifier = defaultButtonModifier,
    ) {
        if (exportUiState.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
        } else {
            Text(text = stringResource(R.string.button_export_to_csv))
        }
    }
}
