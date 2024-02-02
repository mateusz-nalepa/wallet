package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.foundation.border
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview

@Composable
fun MyErrorDialogProxy(
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
) {
    if (errorModalState is ErrorModalState.Visible) {
        MyErrorDialog(
            errorMessage = errorModalState.message,
            onClose = onErrorModalClose
        )
    }
}

// TODO: obramowanie, bo na czarnym tle się wszystko zlewa XD
@Composable
fun MyErrorDialog(
    errorMessage: String,
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Błąd") },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = onClose) {
                Text("Zamknij")
            }
        },
        modifier = Modifier.border(2.dp, MaterialTheme.colors.error)
    )
}

@Preview(showBackground = true)
@Composable
fun MyErrorDialogDarkPreview() {
    SetContentOnDarkPreview {
        MyErrorDialog(
            errorMessage = "Some Error Message",
            onClose = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyErrorDialogLightPreview() {
    SetContentOnLightPreview {
        MyErrorDialog(
            errorMessage = "Some Error Message",
            onClose = {},
        )
    }
}