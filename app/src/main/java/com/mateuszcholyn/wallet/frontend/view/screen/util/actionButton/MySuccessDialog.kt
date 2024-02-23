package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.foundation.border
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview

@Composable
fun MySuccessDialog(
    onClose: () -> Unit,
    successContent: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(stringResource(R.string.success_label)) },
        text = {
            successContent()
        },
        confirmButton = {
            Button(onClick = onClose) {
                Text(stringResource(R.string.common_close))
            }
        },
        modifier = Modifier.border(2.dp, MaterialTheme.colors.primary)
    )
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogDarkPreview() {
    SetContentOnDarkPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            Text(text = "ASD")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogLightPreview() {
    SetContentOnLightPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            Text(text = "ASD")
        }
    }
}
