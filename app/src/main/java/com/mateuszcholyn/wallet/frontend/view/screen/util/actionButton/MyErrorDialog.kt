package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.annotation.StringRes
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
fun MyErrorDialogProxy(
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
) {
    if (errorModalState is ErrorModalState.Visible) {
        MyErrorDialog(
            errorMessageKey = errorModalState.messageKey,
            onClose = onErrorModalClose
        )
    }
}

@Composable
fun MyErrorDialog(
    @StringRes
    errorMessageKey: Int,
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(stringResource(R.string.error_label)) },
        text = { Text(stringResource(errorMessageKey)) },
        confirmButton = {
            Button(onClick = onClose) {
                Text(stringResource(R.string.common_close))
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
            errorMessageKey = R.string.common_export_data,
            onClose = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyErrorDialogLightPreview() {
    SetContentOnLightPreview {
        MyErrorDialog(
            errorMessageKey = R.string.common_export_data,
            onClose = {},
        )
    }
}