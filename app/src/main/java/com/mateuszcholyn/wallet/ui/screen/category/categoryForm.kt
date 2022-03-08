package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.composables.ValidatedTextField
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier

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
                valueInvalidText = stringResource(R.string.incorrectValue),
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

fun categoryIsInvalid(category: String, categories: List<String>): Boolean {
    return category.isBlank() || category in categories
}