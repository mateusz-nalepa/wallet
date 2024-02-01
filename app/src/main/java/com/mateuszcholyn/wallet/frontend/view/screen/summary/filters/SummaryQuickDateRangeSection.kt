package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickDateRanges
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryQuickDateRangeSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    val availableQuickRangeData by remember { mutableStateOf(quickDateRanges()) }

    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = summaryScreenViewModel.summarySearchForm.selectedQuickRangeData,
        availableElements = availableQuickRangeData,
        onItemSelected = { newQuickRangeData ->
            summaryScreenViewModel.updateQuickRangeData(newQuickRangeData)
        },
    )
}