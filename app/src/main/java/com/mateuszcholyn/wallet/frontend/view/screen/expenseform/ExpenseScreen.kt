package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement

@Composable
fun ExpenseScreen(
    onFormSubmitNavigate: () -> Unit,
    onMissingCategoriesNavigate: () -> Unit,
    actualExpenseId: String? = null,
    screenMode: String? = null,
) {
    ExpenseScreenStateless(
        onMissingCategoriesNavigate = onMissingCategoriesNavigate,
        onFormSubmit = onFormSubmitNavigate,
        actualExpenseId = actualExpenseId,
        screenMode = screenMode,
    )
}

@Composable
fun ExpenseScreenStateless(
    onMissingCategoriesNavigate: () -> Unit,
    onFormSubmit: () -> Unit,
    actualExpenseId: String?,
    screenMode: String? = null,
) {

    if (screenMode == "copy" && actualExpenseId != null) {
        CopyExpenseScreen(
            onMissingCategoriesNavigate = onMissingCategoriesNavigate,
            onFormSubmitNavigateAction = onFormSubmit,
            actualExpenseId = actualExpenseId,
        )
    } else if (actualExpenseId == null) {
        AddExpenseScreen(
            onMissingCategoriesNavigate = onMissingCategoriesNavigate,
            onFormSubmitNavigateAction = onFormSubmit,
        )
    } else {
        EditExpenseScreen(
            onMissingCategoriesNavigate = onMissingCategoriesNavigate,
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

