package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

// FIXME: there is no validation for those fields
@Composable
fun AmountRangeSectionStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    Row(modifier = defaultModifier) {
        OutlinedTextField(
            value = summarySearchForm.amountRangeStart,
            onValueChange = { summaryScreenActions.onAmountRangeStartChanged(it) },
            label = { Text(stringResource(R.string.amountFrom)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = defaultModifier.weight(1f),
            singleLine = true,
        )
        OutlinedTextField(
            value = summarySearchForm.amountRangeEnd,
            onValueChange = { summaryScreenActions.onAmountRangeEndChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.amountTo)) },
            modifier = defaultModifier.weight(1f),
            singleLine = true,
        )
    }
}
