package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.datapicker.OutlinedDatePickerField
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryQuickDateRangeSection(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.range),
        selectedElement = summarySearchForm.selectedQuickRangeData,
        availableElements = summarySearchForm.quickDataRanges,
        onItemSelected = { newQuickRangeData ->
            summaryScreenActions.onQuickRangeDataSelected.invoke(newQuickRangeData)
        },
    )
    DateRangeSectionStateless(
        summarySearchForm = summarySearchForm,
        summaryScreenActions = summaryScreenActions,
    )
}

@Composable
fun DateRangeSectionStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    if (!summarySearchForm.showCustomRangeDates) {
        return
    }
    Column(
        modifier = defaultModifier,
    ) {
        OutlinedDatePickerField(
            text = "Data od",
            value = summarySearchForm.beginDate!!,
            onValueChange = { summaryScreenActions.onBeginDateChanged.invoke(it) },
        )
        OutlinedDatePickerField(
            text = "Data do",
            value = summarySearchForm.endDate!!,
            onValueChange = { summaryScreenActions.onEndDateChanged.invoke(it) },
        )
    }
}
