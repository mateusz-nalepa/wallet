package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryQuickDateRangeSection(
    quickDateRanges: List<QuickRangeData>,
    selectedQuickDateRange: QuickRangeData,
    onQuickRangeDataSelected: (QuickRangeData) -> Unit,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = selectedQuickDateRange,
        availableElements = quickDateRanges,
        onItemSelected = { newQuickRangeData ->
            onQuickRangeDataSelected.invoke(newQuickRangeData)
        },
    )
}