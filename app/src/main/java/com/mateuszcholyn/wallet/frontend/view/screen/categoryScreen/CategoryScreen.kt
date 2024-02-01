package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun CategoryScreen(
    categoryScreenViewModel: CategoryScreenViewModel = hiltViewModel(),
) {

    DisposableEffect(key1 = Unit, effect = {
        categoryScreenViewModel.refreshScreen()
        onDispose { }
    })

    val categoryState by remember { categoryScreenViewModel.categoryScreenState }

    Column {
        when (val categoryStateTemp = categoryState) {
            is CategoryScreenState.Error -> ScreenError(categoryStateTemp.errorMessage)
            is CategoryScreenState.Loading -> ScreenLoading()
            is CategoryScreenState.Success -> SuccessCategoryScreen(
                refreshScreenFunction = {
                    categoryScreenViewModel.refreshScreen()
                },
                categorySuccessContent = categoryStateTemp.categorySuccessContent,
            )
        }
    }
}

@Composable
fun SuccessCategoryScreen(
    refreshScreenFunction: () -> Unit,
    categorySuccessContent: CategorySuccessContent,
) {
    NewCategoryForm(
        refreshScreenFunction = refreshScreenFunction,
        categorySuccessContent = categorySuccessContent,
    )
    NumberOfCategories(categorySuccessContent)
    Divider()
    CategoriesList(
        refreshScreenFunction = refreshScreenFunction,
        categorySuccessContent = categorySuccessContent,
    )
}


@Preview(showBackground = true)
@Composable
fun NewCategoryScreenPreview() {
    CategoryScreen()
}

