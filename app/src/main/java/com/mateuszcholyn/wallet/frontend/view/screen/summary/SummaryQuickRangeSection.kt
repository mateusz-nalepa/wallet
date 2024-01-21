package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickRanges

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryQuickRangeSection(
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
    val availableQuickRangeData by remember { mutableStateOf(quickRanges()) }

    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = summaryViewModel.summarySearchForm.selectedQuickRangeData,
        availableElements = availableQuickRangeData,
        onItemSelected = { newQuickRangeData ->
            summaryViewModel.updateQuickRangeData(newQuickRangeData)
        },
    )
}