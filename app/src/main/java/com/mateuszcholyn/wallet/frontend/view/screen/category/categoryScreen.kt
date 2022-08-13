package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.summary.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryError

@Composable
fun NewCategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    categoryViewModel.refreshScreen()
    NewCategoryScreenContent()
}


@Composable
fun NewCategoryScreenContent(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {

    val categoryState by remember { categoryViewModel.categoryState }

    Column {
        when (val categoryStateTemp = categoryState) {
            is CategoryState.Error -> SummaryError(categoryStateTemp.errorMessage)
            is CategoryState.Loading -> ScreenLoading("Category Loading")
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
    NewCategoryScreen()
}

