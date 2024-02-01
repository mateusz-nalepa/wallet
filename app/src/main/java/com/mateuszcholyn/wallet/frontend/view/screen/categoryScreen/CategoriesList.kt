package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.SingleCategory

@Composable
fun CategoriesList(
    refreshScreenFunction: () -> Unit,
    categorySuccessContent: CategorySuccessContent,
) {
    CategoriesListStateless(
        refreshScreenFunction = refreshScreenFunction,
        categoryListOptions = categorySuccessContent.categoriesList,
        categorySuccessContent = categorySuccessContent,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoriesListStateless(
    refreshScreenFunction: () -> Unit,
    categoryListOptions: List<CategoryQuickSummary>,
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
                refreshScreenFunction = refreshScreenFunction,
                categoryQuickSummary = categoryQuickSummary,
                categorySuccessContent = categorySuccessContent,
            )
        }
    }

}