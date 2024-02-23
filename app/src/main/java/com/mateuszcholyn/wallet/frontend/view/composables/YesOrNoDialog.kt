package com.mateuszcholyn.wallet.frontend.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun YesOrNoDialog(
    openDialog: Boolean,
    onDialogClosed: () -> Unit,
    content: @Composable () -> Unit = {},
    onConfirm: () -> Unit,
    onCancel: () -> Unit = {},
    cancelText: String = stringResource(R.string.common_cancel),
    confirmText: String = stringResource(R.string.common_confirm),
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onDialogClosed.invoke()
            },
            text = content,
            buttons = {
                Row(
                    modifier = defaultModifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = defaultModifier.weight(1f),
                        onClick = {
                            onDialogClosed.invoke()
                            onCancel.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    ) {
                        Text(cancelText)
                    }
                    Button(
                        modifier = defaultModifier.weight(1f),
                        onClick = {
                            onDialogClosed.invoke()
                            onConfirm.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
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
        openDialog = true,
        onDialogClosed = {},
        content = {
            Text(text = "some example yes or no content")
        },
        onConfirm = {},
    )
}