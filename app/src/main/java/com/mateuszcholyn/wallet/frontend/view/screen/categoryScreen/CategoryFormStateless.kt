package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextFieldV2
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


enum class CategorySubmitButton {
    DISABLED,
    ENABLED,
    LOADING,
}

data class CategoryFormUiState(
    val categoryName: String = EMPTY_STRING,
    val isFormInvalid: Boolean = false,
    val submitButtonState: CategorySubmitButton = CategorySubmitButton.DISABLED,
    val showCategoryAlreadyExistsWarning: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
    val buttonLabel: String = "Dodaj",
)

data class CategoryFormUiActions(
    val onCategoryValueChanged: (String) -> Unit,
    val onErrorModalClose: () -> Unit,
    val onFormSubmit: () -> Unit,
)


@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryFormStateless(
    categoryFormUiState: CategoryFormUiState,
    categoryFormUiActions: CategoryFormUiActions,
) {
    Column(modifier = defaultModifier) {
        ValidatedTextFieldV2(
            textFieldLabel = "Kategoria",
            value = categoryFormUiState.categoryName,
            onValueChange = {
                categoryFormUiActions.onCategoryValueChanged.invoke(it)
            },
            isValueInvalid = categoryFormUiState.isFormInvalid,
            valueInvalidText = "Nieprawidłowa nazwa kategorii",
            modifier = defaultModifier,
        )
        if (categoryFormUiState.showCategoryAlreadyExistsWarning) {
            Text(
                text = "Kategoria o takiej nazwie już istnieje.",
                color = MaterialTheme.colors.primary,
                modifier = defaultModifier,
            )
        }

        MyErrorDialogProxy(
            errorModalState = categoryFormUiState.errorModalState,
            onErrorModalClose = categoryFormUiActions.onErrorModalClose
        )

        Button(
            enabled = categoryFormUiState.submitButtonState == CategorySubmitButton.ENABLED,
            onClick = {
                categoryFormUiActions.onFormSubmit.invoke()
            },
            modifier = defaultButtonModifier,
        ) {
            if (categoryFormUiState.submitButtonState == CategorySubmitButton.LOADING) {
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
            } else {
                Text(categoryFormUiState.buttonLabel)
            }
        }
    }
}
