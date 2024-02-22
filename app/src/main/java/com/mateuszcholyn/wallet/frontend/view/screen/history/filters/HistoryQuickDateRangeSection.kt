package com.mateuszcholyn.wallet.frontend.view.screen.history.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.datapicker.OutlinedDatePickerField
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryQuickDateRangeSection(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = historySearchForm.selectedQuickRangeData,
        availableElements = historySearchForm.quickDataRanges,
        onItemSelected = { newQuickRangeData ->
            historyScreenActions.onQuickRangeDataSelected.invoke(newQuickRangeData)
        },
    )
    DateRangeSectionStateless(
        historySearchForm = historySearchForm,
        historyScreenActions = historyScreenActions,
    )
}

@Composable
fun DateRangeSectionStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
) {
    if (!historySearchForm.showCustomRangeDates) {
        return
    }
    Column(
        modifier = defaultModifier,
    ) {
        OutlinedDatePickerField(
            text = "Data od",
            value = historySearchForm.beginDate!!,
            onValueChange = { historyScreenActions.onBeginDateChanged.invoke(it) },
        )
        OutlinedDatePickerField(
            text = "Data do",
            value = historySearchForm.endDate!!,
            onValueChange = { historyScreenActions.onEndDateChanged.invoke(it) },
        )
    }
}
