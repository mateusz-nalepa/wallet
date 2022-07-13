package com.mateuszcholyn.wallet.ui.screen.summary.advancedFilters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.ui.screen.summary.SummaryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummarySortingSection(
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.Sorting),
        selectedElement = summaryViewModel.selectedSortElement,
        availableElements = summaryViewModel.availableSortingElements,
        onItemSelected = { newSortElement ->
            summaryViewModel.updateSortElement(newSortElement)
        },
    )
}