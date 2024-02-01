package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    categoryViewModel.refreshScreen()

    val categoryState by remember { categoryViewModel.categoryState }

    Column {
        when (val categoryStateTemp = categoryState) {
            is CategoryState.Error -> ScreenError(categoryStateTemp.errorMessage)
            is CategoryState.Loading -> ScreenLoading()
            is CategoryState.Success -> SuccessCategoryScreen(categoryStateTemp.categorySuccessContent)
        }
    }
}

@Composable
fun SuccessCategoryScreen(categorySuccessContent: CategorySuccessContent) {
    NewCategoryForm(categorySuccessContent)
    NumberOfCategories(categorySuccessContent)
    Divider()
    CategoriesList(categorySuccessContent)
}


@Preview(showBackground = true)
@Composable
fun NewCategoryScreenPreview() {
    CategoryScreen()
}

