package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    @StringRes
    val titleKey: Int,
    @StringRes
    val keepLeftTextKey: Int,
    val onKeepLeft: () -> Unit,
    @StringRes
    val keepRightTextKey: Int,
    val onKeepRight: () -> Unit,
    @StringRes
    val leftValuesQuickSummaryKey: Int,
    @StringRes
    val rightValuesQuickSummaryKey: Int,
    val comparableData: List<ComparableData>,
)

data class ComparableData(
    @StringRes
    val valueTitleKey: Int,
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
                confirmText = stringResource(comparatorModalDialogState.comparatorModalParameters.keepRightTextKey),
                cancelText = stringResource(comparatorModalDialogState.comparatorModalParameters.keepLeftTextKey),
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
                text = stringResource(comparatorModalParameters.titleKey),
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
            Text(
                text = stringResource(comparatorModalParameters.leftValuesQuickSummaryKey),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(comparatorModalParameters.rightValuesQuickSummaryKey),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
            )
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
            text = stringResource(comparableData.valueTitleKey),
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

