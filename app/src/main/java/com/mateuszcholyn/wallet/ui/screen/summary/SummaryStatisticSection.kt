package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun SummaryStatisticSection(
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
    summaryViewModel.updateSummaryResultText(stringResource(R.string.defaultSummaryResultText))

    Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
        Text(
            text = stringResource(R.string.quantity) + " ${summaryViewModel.expensesList.size}",
            modifier = defaultModifier.weight(1f)
        )
    }
    Row(modifier = defaultModifier.padding(top = 0.dp)) {
        Text(text = summaryViewModel.summaryResultText, modifier = defaultModifier.weight(2f))
    }
}