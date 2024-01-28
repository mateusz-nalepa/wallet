package com.mateuszcholyn.wallet.frontend.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun YesOrNoDialog(
    openDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit = {},
    message: String = stringResource(R.string.areYouReadyToRemoveExpense),
    cancelText: String = stringResource(R.string.yesOrNoCancel),
    confirmText: String = stringResource(R.string.yesOrNoConfirm),
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = message)
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = defaultModifier.weight(1f),
                        onClick = {
                            openDialog.value = false
                            onCancel.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
                    ) {
                        Text(cancelText)
                    }
                    Button(
                        modifier = defaultModifier.weight(1f),
                        onClick = {
                            openDialog.value = false
                            onConfirm.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    ) {
                        Text(confirmText)
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun YesOrNoDialogPreview() {
    YesOrNoDialog(
        openDialog = remember { mutableStateOf(true) },
        onConfirm = {},
    )
}