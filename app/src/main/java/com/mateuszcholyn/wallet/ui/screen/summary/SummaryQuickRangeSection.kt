package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryQuickRangeSection(
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = summaryViewModel.selectedQuickRangeData,
        availableElements = summaryViewModel.availableQuickRangeDataV2,
        onItemSelected = { newQuickRangeData ->
            summaryViewModel.updateQuickRangeData(newQuickRangeData)
        },
    )
}