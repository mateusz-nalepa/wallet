package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryGroupingDropdownSectionStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.grouping),
        selectedElement = summarySearchForm.selectedGroupElement,
        availableElements = summarySearchForm.groupingElements,
        onItemSelected = { newGroupElement ->
            summaryScreenActions.onGroupingElementSelected.invoke(newGroupElement)
        },
        isEnabled = summarySearchForm.isGroupingEnabled,
    )
}