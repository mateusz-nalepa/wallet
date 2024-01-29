package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    val isFormValid by remember {
        derivedStateOf {
            !categoryIsInvalid(newCategoryNameText)
        }
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

private fun categoryIsInvalid(category: String): Boolean =
    category.isBlank()

private fun shouldShowCategoryWarning(category: String, categories: List<String>): Boolean =
    category != "" && category in categories