package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier

sealed interface ErrorModalState {
    data object NotVisible : ErrorModalState
    data class Visible(val messageKey: Int) : ErrorModalState
}

sealed interface SuccessModalState {
    data object NotVisible : SuccessModalState

    // FIXME: val importV1Summary: ImportV1Summary should not be here XD
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
            errorMessageKey = errorModalState.messageKey,
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
