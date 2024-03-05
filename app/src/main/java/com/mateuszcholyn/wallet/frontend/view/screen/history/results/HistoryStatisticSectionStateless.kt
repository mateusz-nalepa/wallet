package com.mateuszcholyn.wallet.frontend.view.screen.history.results

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySuccessContent
import com.mateuszcholyn.wallet.frontend.view.util.BigDecimalAsFormattedAmountFunction
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun HistoryStatisticSectionStateless(
    bigDecimalAsFormattedAmountFunction: BigDecimalAsFormattedAmountFunction,
    historySuccessContent: HistorySuccessContent,
) {
    Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
        Text(
            text = stringResource(R.string.common_quantity) + " ${historySuccessContent.expensesList.size}",
            modifier = defaultModifier.weight(1f)
        )
    }
    Row(modifier = defaultModifier.padding(top = 0.dp)) {
        Text(
            text = historySuccessContent.summaryResult.asTextSummary(
                bigDecimalAsFormattedAmountFunction
            ),
            modifier = defaultModifier.weight(2f),
        )
    }
}

@Composable
fun SearchAverageExpenseResult.asTextSummary(
    bigDecimalAsFormattedAmountFunction: BigDecimalAsFormattedAmountFunction,
): String =
    "${bigDecimalAsFormattedAmountFunction.invoke(wholeAmount)} / ${days}d = ${
        bigDecimalAsFormattedAmountFunction.invoke(
            averageAmount
        )
    }/d"