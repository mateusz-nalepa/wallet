package com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.userConfig.theme.LocalWalletThemeComposition

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
    successModalContent: @Composable  ()-> Unit = {},
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
