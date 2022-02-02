package com.mateuszcholyn.wallet.scaffold.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun CategoryForm(
        textFieldLabel: String,
        buttonLabel: String,
        initialCategoryName: String = "",
        categoryNamesOnly: List<String> = emptyList(),
        onFormSubmit: (String) -> Unit,
) {

    var categoryNameText by remember { mutableStateOf(initialCategoryName) }

    val isFormValid by derivedStateOf {
        !categoryIsInvalid(categoryNameText, categoryNamesOnly)
    }

    Column(modifier = defaultModifier) {
        ValidatedTextField(
                textFieldLabel = textFieldLabel,
                value = categoryNameText,
                onValueChange = { categoryNameText = it },
                isValueInValidFunction = {
                    categoryIsInvalid(it, categoryNamesOnly)
                },
                valueInvalidText = "Nieprawidłowa wartość",
                modifier = defaultModifier,
        )
        Button(
                enabled = isFormValid,
                onClick = {
                    onFormSubmit.invoke(categoryNameText)
                    categoryNameText = ""
                },
                modifier = defaultButtonModifier,
        ) {
            Text(buttonLabel)
        }
    }
}