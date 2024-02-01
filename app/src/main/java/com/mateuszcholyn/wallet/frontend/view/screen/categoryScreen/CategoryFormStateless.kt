package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextField
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialog
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryFormStateless(
    submitButtonIsLoading: Boolean,
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
    textFieldLabel: String,
    buttonLabel: String,
    initialCategoryName: String = EMPTY_STRING,
    categoryNamesOnly: List<String> = emptyList(),
    onFormSubmit: (String) -> Unit,
) {

    var newCategoryNameText by remember { mutableStateOf(initialCategoryName) }

    val isFormValid by derivedStateOf {
        !categoryIsInvalid(newCategoryNameText)
    }


    var categoryAlreadyExists by remember { mutableStateOf(false) }

    Column(modifier = defaultModifier) {
        ValidatedTextField(
            textFieldLabel = textFieldLabel,
            value = newCategoryNameText,
            onValueChange = {
                categoryAlreadyExists = shouldShowCategoryWarning(it, categoryNamesOnly)
                newCategoryNameText = it
            },
            isValueInValidFunction = {
                categoryIsInvalid(it)
            },
            valueInvalidText = "Nieprawidłowa nazwa kategorii",
            modifier = defaultModifier,
        )
        if (categoryAlreadyExists) {
            Text(
                text = "Kategoria o takiej nazwie już istnieje. Wciąż jednak możesz dodać kolejną kategorię o takiej nazwie, jeśli chcesz.",
                color = MaterialTheme.colors.primary,
                modifier = defaultModifier,
            )
        }

        if (errorModalState is ErrorModalState.Visible) {
            MyErrorDialog(
                errorMessage = errorModalState.message,
                onClose = onErrorModalClose
            )
        }
        Button(
            enabled = isFormValid,
            onClick = {
                onFormSubmit.invoke(newCategoryNameText)
                newCategoryNameText = EMPTY_STRING
            },
            modifier = defaultButtonModifier,
        ) {
            if (submitButtonIsLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
            } else {
                Text(buttonLabel)
            }
        }
    }
}

private fun categoryIsInvalid(category: String): Boolean =
    category.isBlank()

private fun shouldShowCategoryWarning(category: String, categories: List<String>): Boolean =
    category != "" && category in categories
