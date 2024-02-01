package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AmountRangeSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    Row(modifier = defaultModifier) {
        OutlinedTextField(
            value = summaryScreenViewModel.summarySearchForm.amountRangeStart,
            onValueChange = { summaryScreenViewModel.updateAmountRangeStart(it) },
            label = { Text(stringResource(R.string.amountFrom)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = defaultModifier.weight(1f),
            singleLine = true,
        )
        OutlinedTextField(
            value = summaryScreenViewModel.summarySearchForm.amountRangeEnd,
            onValueChange = { summaryScreenViewModel.updateAmountRangeEnd(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.amountTo)) },
            modifier = defaultModifier.weight(1f),
            singleLine = true,
        )
    }

}