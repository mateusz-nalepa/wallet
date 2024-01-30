package com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement

@Composable
fun NewAddOrEditExpenseScreen(
    onFormSubmitNavigate: () -> Unit,
    onMissingCategoriesNavigate: () -> Unit,
    actualExpenseId: String? = null,
    screenMode: String? = null,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel()
) {
    val categoryNameOptions by remember { addOrEditExpenseViewModel.categoryOptions }
    if (categoryNameOptions.isEmpty()) {
        NoCategoryPresentInfo(onMissingCategoriesNavigate)
        return
    }
    ShowAddOrEditExpenseScreenContent(
        onFormSubmit = onFormSubmitNavigate,
        actualExpenseId = actualExpenseId,
        screenMode = screenMode,
    )
}

@Composable
fun ShowAddOrEditExpenseScreenContent(
    onFormSubmit: () -> Unit,
    actualExpenseId: String?,
    screenMode: String? = null,
) {

    if (screenMode == "copy" && actualExpenseId != null) {
        CopyExpenseScreen(
            onFormSubmitNavigateAction = onFormSubmit,
            actualExpenseId = actualExpenseId,
        )
    } else if (actualExpenseId == null) {
        AddExpenseScreen(
            onFormSubmitNavigateAction = onFormSubmit,
        )
    } else {
        EditExpenseScreen(
            onFormSubmitNavigateAction = onFormSubmit,
            actualExpenseId = actualExpenseId,
        )
    }
}

data class CategoryView(
    override val name: String,
    override val nameKey: Int? = null,
    val categoryId: String? = null,
) : DropdownElement

