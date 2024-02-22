package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedFilters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryGroupingDropdownSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.grouping),
        selectedElement = historySearchForm.selectedGroupingElement,
        availableElements = historySearchForm.groupingElements,
        onItemSelected = { newGroupElement ->
            historyScreenActions.onGroupingElementSelected.invoke(newGroupElement)
        },
        isEnabled = historySearchForm.isGroupingEnabled,
    )
}