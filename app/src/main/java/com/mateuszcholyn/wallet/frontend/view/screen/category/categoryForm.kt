package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextField
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
fun NewCategoryForm(
    categorySuccessContent: CategorySuccessContent,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    CategoryFormStateless(
        textFieldLabel = stringResource(R.string.newCategoryName),
        buttonLabel = stringResource(R.string.addCategory),
        onFormSubmit = { newCategory -> categoryViewModel.addCategory(newCategory) },
        categoryNamesOnly = categorySuccessContent.categoryNamesOnly,
    )
}

@Composable
fun EditCategoryForm(
    actualCategoryName: String,
    onFormSubmit: (NewCategoryName) -> Unit,
    categorySuccessContent: CategorySuccessContent,
) {
    CategoryFormStateless(
        textFieldLabel = stringResource(R.string.updatedCategoryName),
        buttonLabel = stringResource(R.string.update),
        initialCategoryName = actualCategoryName,
        onFormSubmit = onFormSubmit,
        categoryNamesOnly = categorySuccessContent.categoryNamesOnly,
    )
}

typealias NewCategoryName = String

@Composable
private fun CategoryFormStateless(
    textFieldLabel: String,
    buttonLabel: String,
    initialCategoryName: String = EMPTY_STRING,
    categoryNamesOnly: List<String> = emptyList(),
    onFormSubmit: (NewCategoryName) -> Unit,
) {

    var newCategoryNameText by remember { mutableStateOf(initialCategoryName) }

    val isFormValid by derivedStateOf {
        !categoryIsInvalid(newCategoryNameText, categoryNamesOnly)
    }

    Column(modifier = defaultModifier) {
        ValidatedTextField(
            textFieldLabel = textFieldLabel,
            value = newCategoryNameText,
            onValueChange = { newCategoryNameText = it },
            isValueInValidFunction = {
                categoryIsInvalid(it, categoryNamesOnly)
            },
            valueInvalidText = stringResource(R.string.categoryAlreadyExists),
            modifier = defaultModifier,
        )
        Button(
            enabled = isFormValid,
            onClick = {
                onFormSubmit.invoke(newCategoryNameText)
                newCategoryNameText = EMPTY_STRING
            },
            modifier = defaultButtonModifier,
        ) {
            Text(buttonLabel)
        }
    }
}

private fun categoryIsInvalid(category: String, categories: List<String>): Boolean {
    return category.isBlank() || category in categories
}