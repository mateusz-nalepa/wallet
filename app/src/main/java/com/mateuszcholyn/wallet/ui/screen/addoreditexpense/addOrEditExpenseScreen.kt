package com.mateuszcholyn.wallet.ui.screen.addoreditexpense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import com.mateuszcholyn.wallet.ui.dropdown.DropdownElement

@Composable
fun NewAddOrEditExpenseScreen(
    onFormSubmitNavigate: () -> Unit,
    onMissingCategoriesNavigate: () -> Unit,
    actualExpenseId: Long? = null,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel()
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }
    if (categoryNameOptions.isEmpty()) {
        NoCategoryPresentInfo(onMissingCategoriesNavigate)
        return
    }
    ShowAddOrEditExpenseScreenContent(
        onFormSubmit = onFormSubmitNavigate,
        actualExpenseId = actualExpenseId,
    )
}

@Composable
fun ShowAddOrEditExpenseScreenContent(
    onFormSubmit: () -> Unit,
    actualExpenseId: Long?,
) {
    if (actualExpenseId == null) {
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
    val id: Long? = null,
    val isAllCategories: Boolean = false,
) : DropdownElement

fun CategoryDetails.toCategoryView(): CategoryView =
    CategoryView(
        id = id,
        name = name,
    )

fun ExistingCategory.toCategoryView(): CategoryView =
    CategoryView(
        id = id,
        name = name,
    )

fun CategoryView.toExistingCategory(): ExistingCategory =
    ExistingCategory(
        id = id!!,
        name = name,
    )
