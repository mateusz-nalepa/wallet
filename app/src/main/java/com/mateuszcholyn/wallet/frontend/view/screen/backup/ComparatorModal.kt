package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog


sealed interface ComparatorModalDialogState {
    data object NotVisible : ComparatorModalDialogState
    data class Visible(
        val comparatorModalParameters: ComparatorModalParameters,
    ) : ComparatorModalDialogState
}


data class ComparatorModalParameters(
    val title: String,
    val keepLeftText: String,
    val onKeepLeft: () -> Unit,
    val keepRightText: String,
    val onKeepRight: () -> Unit,
    val leftValuesQuickSummary: String,
    val rightValuesQuickSummary: String,
    val comparableData: List<ComparableData>,
)

data class ComparableData(
    val valueTitle: String,
    val leftValue: String,
    val rightValue: String,
)

@Composable
fun ComparatorModal(
    onComparatorModalDialogClosed: () -> Unit,
    comparatorModalDialogState: ComparatorModalDialogState,
) {
    when (comparatorModalDialogState) {
        is ComparatorModalDialogState.NotVisible -> {}
        is ComparatorModalDialogState.Visible -> {
            YesOrNoDialog(
                content = {
                    Comparator(
                        comparatorModalDialogState.comparatorModalParameters
                    )
                },
                confirmText = comparatorModalDialogState.comparatorModalParameters.keepRightText,
                cancelText = comparatorModalDialogState.comparatorModalParameters.keepLeftText,
                openDialog = true,
                onCancel = {
                    comparatorModalDialogState.comparatorModalParameters.onKeepLeft.invoke()
                },
                onConfirm = {
                    comparatorModalDialogState.comparatorModalParameters.onKeepRight.invoke()
                },
                onDialogClosed = onComparatorModalDialogClosed,
            )

        }
    }


}

@Composable
private fun Comparator(
    comparatorModalParameters: ComparatorModalParameters,
) {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Text(
                text = comparatorModalParameters.title,
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
            )
        }
        Divider()
        Divider(
            color = MaterialTheme.colors.background,
            thickness = 5.dp,
        )
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Text(text = comparatorModalParameters.leftValuesQuickSummary, modifier = Modifier.weight(1f))
            Text(text = comparatorModalParameters.rightValuesQuickSummary, modifier = Modifier.weight(1f))
        }
        Divider()
        Divider(
            color = MaterialTheme.colors.background,
            thickness = 5.dp,
        )
        comparatorModalParameters.comparableData.forEach {
            ComparableRow(it)
        }
    }
}

@Composable
private fun ComparableRow(
    comparableData: ComparableData,
) {
    val color =
        if (comparableData.leftValue != comparableData.rightValue) {
            MaterialTheme.colors.error
        } else {
            Color.Unspecified
        }

    Row {
        Text(
            text = comparableData.valueTitle,
            color = color,
        )
        if (color == MaterialTheme.colors.error) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Text(color = color, text = comparableData.leftValue, modifier = Modifier.weight(1f))
        Text(color = color, text = comparableData.rightValue, modifier = Modifier.weight(1f))
    }
    Divider()
}

//@Preview(showBackground = true)
//@Composable
//fun ExpenseChangedModalDarkPreview(
//    @PreviewParameter(ExpensesToCompareParameterProvider::class) expensesToCompare: ExpensesToCompare,
//) {
//    SetContentOnDarkPreview {
//        ExpenseChangedModal(
//            remember {
//                mutableStateOf(true)
//            },
//            onKeepExpenseFromDatabase = {},
//            onUseExpenseFromBackup = {},
//            expensesToCompare = expensesToCompare,
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ExpenseChangedModalLightPreview(
//    @PreviewParameter(ExpensesToCompareParameterProvider::class) expensesToCompare: ExpensesToCompare,
//) {
//    SetContentOnLightPreview {
//        ExpenseChangedModal(
//            remember {
//                mutableStateOf(true)
//            },
//            onKeepExpenseFromDatabase = {},
//            onUseExpenseFromBackup = {},
//            expensesToCompare = expensesToCompare,
//        )
//    }
//}
//
//class ExpensesToCompareParameterProvider : PreviewParameterProvider<ComparableDataModalParameters> {
//    private val now = Instant.now()
//    override val values: Sequence<ComparableDataModalParameters> =
//        sequenceOf(
//            ComparableDataModalParameters(
//                expenseFromBackup = ComparableExpense(
//                    categoryName = "Zakupy",
//                    amount = BigDecimal.valueOf(50),
//                    paidAt = now,
//                    description = "",
//                ),
//                expenseFromDatabase = ComparableExpense(
//                    categoryName = "Paliwo",
//                    amount = BigDecimal.valueOf(50),
//                    paidAt = now,
//                    description = "",
//                )
//            )
//        )
//}
