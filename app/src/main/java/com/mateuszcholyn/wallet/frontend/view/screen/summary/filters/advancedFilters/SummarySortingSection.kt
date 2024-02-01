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
import com.mateuszcholyn.wallet.frontend.view.dropdown.sortingElements
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummarySortingSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    val availableSortingElements by remember { mutableStateOf(sortingElements()) }

    WalletDropdown(
        dropdownName = stringResource(R.string.Sorting),
        selectedElement = summaryScreenViewModel.summarySearchForm.selectedSortElement,
        availableElements = availableSortingElements,
        onItemSelected = { newSortElement ->
            summaryScreenViewModel.updateSortElement(newSortElement)
        },
    )
}