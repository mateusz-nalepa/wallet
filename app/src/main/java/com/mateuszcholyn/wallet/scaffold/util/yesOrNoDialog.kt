package com.mateuszcholyn.wallet.scaffold.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun YesOrNoDialog(openDialog: MutableState<Boolean>, onConfirm: () -> Unit) {
    if (openDialog.value) {
        AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Na pewno usunąć wydatek?")
                },
                buttons = {
                    Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                                modifier = defaultModifier.weight(1f),
                                onClick = { openDialog.value = false },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        ) {
                            Text("Anuluj")
                        }
                        Button(
                                modifier = defaultModifier.weight(1f),
                                onClick = {
                                    openDialog.value = false
                                    onConfirm.invoke()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        ) {
                            Text("Potwierdź")
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
            openDialog = remember { mutableStateOf(false) },
            onConfirm = {},
    )
}