package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService

@Composable
fun CategoriesList(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    CategoriesListStateless(
        categoryListOptions = categoryViewModel.categoryOptions,
        categoryService = categoryViewModel.categoryService(),
        refreshCategoryListFunction = {
            categoryViewModel.refreshCategoryList()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoriesListStateless(
    categoryListOptions: List<CategoryDetails>,
    categoryService: CategoryService,
    refreshCategoryListFunction: () -> Unit,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),

        ) {
        items(categoryListOptions) { categoryDetails ->
            SingleCategory(
                categoryService = categoryService,
                categoryDetails = categoryDetails,
                refreshCategoryListFunction = { refreshCategoryListFunction() },
            )
        }
    }

}