package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.ui.composables.ValidatedTextField
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.EMPTY_STRING


@Composable
fun NewCategoryForm(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    CategoryFormStateless(
        textFieldLabel = stringResource(R.string.newCategoryName),
        buttonLabel = stringResource(R.string.addCategory),
        onFormSubmit = { newCategory ->
            categoryViewModel.addCategory(newCategory)
        },
        categoryNamesOnly = categoryViewModel.categoryNamesOnly,
    )
}

@Composable
fun EditCategoryForm(
    actualCategoryName: String,
    onFormSubmit: (Category) -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    CategoryFormStateless(
        textFieldLabel = stringResource(R.string.updatedCategoryName),
        buttonLabel = stringResource(R.string.update),
        initialCategoryName = actualCategoryName,
        onFormSubmit = onFormSubmit,
        categoryNamesOnly = categoryViewModel.categoryNamesOnly,
    )
}

@Composable
private fun CategoryFormStateless(
    textFieldLabel: String,
    buttonLabel: String,
    initialCategoryName: String = EMPTY_STRING,
    categoryNamesOnly: List<String> = emptyList(),
    onFormSubmit: (Category) -> Unit,
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
                onFormSubmit.invoke(Category(name = categoryNameText))
                categoryNameText = EMPTY_STRING
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