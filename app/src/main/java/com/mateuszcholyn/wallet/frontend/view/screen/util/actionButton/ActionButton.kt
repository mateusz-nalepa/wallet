package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier

sealed interface ErrorModalState {
    data object NotVisible : ErrorModalState
    data class Visible(val message: String) : ErrorModalState
}

sealed interface SuccessModalState {
    data object NotVisible : SuccessModalState
    data class Visible(val unit: @Composable () -> Unit) : SuccessModalState
}

@Composable
fun ActionButton(
    isLoading: Boolean,
    text: String,
    onClick: () -> Unit,
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
    successModalState: SuccessModalState = SuccessModalState.NotVisible,
    onSuccessModalClose: () -> Unit = {},
) {

    if (errorModalState is ErrorModalState.Visible) {
        MyErrorDialog(
            errorMessage = errorModalState.message,
            onClose = onErrorModalClose
        )
    }

    if (successModalState is SuccessModalState.Visible) {
        MySuccessDialog(
            successContent = successModalState.unit,
            onClose = onSuccessModalClose
        )
    }

    Button(
        onClick = onClick,
        modifier = defaultButtonModifier,
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
        } else {
            Text(text)
        }
    }
}


@Composable
fun MyErrorDialogV2(
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
        }
    )
}

@Composable
fun MySuccessDialog(
    successContent: @Composable () -> Unit,
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        // TODO: title nie jest wymagane
        title = { Text("Sukces!") },
        // TODO: tutaj może być dowolny composable
        text = {
            successContent()
        },
        confirmButton = {
            Button(onClick = onClose) {
                Text("Zamknij")
            }
        }
    )
}
