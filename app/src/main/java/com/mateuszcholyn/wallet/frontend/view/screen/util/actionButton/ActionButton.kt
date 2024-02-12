package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier

// TODO: pewnie do wywalenia ta klasa
data class ButtonActions(
    val onSuccessAction: () -> Unit,
    val onErrorAction: (String) -> Unit,
)

sealed interface ErrorModalState {
    data object NotVisible : ErrorModalState
    data class Visible(val message: String) : ErrorModalState
}

sealed interface SuccessModalState {
    data object NotVisible : SuccessModalState

    // TODO tu pewnie nie powinno być na sztywno ImportV1Summary XD
    data class Visible(val importV1Summary: ImportV1Summary) : SuccessModalState
}

@Composable
fun ActionButton(
    isLoading: Boolean,
    text: String,
    onClick: () -> Unit,
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
    successModalState: SuccessModalState = SuccessModalState.NotVisible,
    successModalContent: @Composable () -> Unit = {},
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
            successContent = successModalContent,
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
