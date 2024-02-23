package com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.orDefaultDescription
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.copy.CopySingleExpenseIconButtonStateless
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.edit.EditSingleExpenseIconButtonStateless
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseIconButton
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

@Composable
fun ShowSingleExpenseDetails(
    searchSingleResult: SearchSingleResult,
    historyScreenActions: HistoryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    Column {
        Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
            Icon(
                Icons.Filled.Description,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Text(text = searchSingleResult.description.orDefaultDescription(stringResource(R.string.common_noDescription)))
        }
        Row(
            modifier = defaultModifier.padding(bottom = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    Icons.Filled.Event,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = searchSingleResult.paidAt.fromUTCInstantToUserLocalTimeZone()
                        .toHumanDateTimeText()
                )
            }

            Row(horizontalArrangement = Arrangement.End) {
                SingleExpenseDetailsActions(
                    searchSingleResult,
                    historyScreenActions,
                    removeSingleExpenseUiState,
                )
            }
        }
    }
}

@Composable
fun SingleExpenseDetailsActions(
    searchSingleResult: SearchSingleResult,
    historyScreenActions: HistoryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    CopySingleExpenseIconButtonStateless(
        expenseId = searchSingleResult.expenseId,
        historyScreenActions = historyScreenActions,
    )
    EditSingleExpenseIconButtonStateless(
        expenseId = searchSingleResult.expenseId,
        historyScreenActions = historyScreenActions,
    )
    RemoveSingleExpenseIconButton(
        expenseId = searchSingleResult.expenseId,
        historyScreenActions = historyScreenActions,
        removeSingleExpenseUiState = removeSingleExpenseUiState,
    )
}
