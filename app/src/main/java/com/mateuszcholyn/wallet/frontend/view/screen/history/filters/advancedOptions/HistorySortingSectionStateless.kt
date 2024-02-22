package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistorySortingSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.Sorting),
        selectedElement = historySearchForm.selectedSortElement,
        availableElements = historySearchForm.sortElements,
        onItemSelected = { newSortElement ->
            historyScreenActions.onSortElementSelected(newSortElement)
        },
    )
}
