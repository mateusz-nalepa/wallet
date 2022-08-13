package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId

@Composable
fun CategoriesList(
    categorySuccessContent: CategorySuccessContent,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    CategoriesListStateless(
        categoryListOptions = categorySuccessContent.categoriesList,
        onDeleteFunction = { categoryId -> categoryViewModel.deleteCategory(categoryId) },
        onUpdateCategory = { updatedCategory -> categoryViewModel.updateCategory(updatedCategory) },
        categorySuccessContent = categorySuccessContent,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoriesListStateless(
    categoryListOptions: List<CategoryQuickSummary>,
    onDeleteFunction: (CategoryId) -> Unit,
    onUpdateCategory: (CategoryQuickSummary) -> Unit,
    categorySuccessContent: CategorySuccessContent,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),

        ) {
        items(categoryListOptions) { categoryQuickSummary ->
            SingleCategory(
                onDeleteFunction = onDeleteFunction,
                onUpdateCategoryFunction = onUpdateCategory,
                categoryQuickSummary = categoryQuickSummary,
                categorySuccessContent = categorySuccessContent,
            )
        }
    }

}