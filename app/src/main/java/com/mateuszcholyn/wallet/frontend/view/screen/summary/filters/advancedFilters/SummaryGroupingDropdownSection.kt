package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.dropdown.groupingDataXD
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryGroupingDropdownSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    val availableGroupElements by remember { mutableStateOf(groupingDataXD()) }

    WalletDropdown(
        dropdownName = stringResource(R.string.grouping),
        selectedElement = summaryScreenViewModel.summarySearchForm.selectedGroupElement,
        availableElements = availableGroupElements,
        onItemSelected = { newGroupElement ->
            summaryScreenViewModel.updateGroupElement(newGroupElement)
        },
        isEnabled = summaryScreenViewModel.summarySearchForm.isGroupingEnabled,
    )
}