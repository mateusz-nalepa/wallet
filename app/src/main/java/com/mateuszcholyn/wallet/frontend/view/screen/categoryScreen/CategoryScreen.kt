package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.categoryFormScreenRoute
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

data class CategoryScreenActions(
    val onRefreshScreenActions: () -> Unit,
    val onAddCategoryAction: () -> Unit,
    val onUpdateCategoryAction: (CategoryId) -> Unit,
)

@Composable
fun CategoryScreen(
    navHostController: NavHostController,
    categoryScreenViewModel: CategoryScreenViewModel = hiltViewModel(),
) {

    DisposableEffect(key1 = Unit, effect = {
        categoryScreenViewModel.refreshScreen()
        onDispose { }
    })

    val categoryScreenState by remember { categoryScreenViewModel.categoryScreenState }

    val categoryScreenActions =
        CategoryScreenActions(
            onRefreshScreenActions = {
                categoryScreenViewModel.refreshScreen()
            },
            onAddCategoryAction = {
                navHostController.navigate(categoryFormScreenRoute())
            },
            onUpdateCategoryAction = {
                navHostController.navigate(categoryFormScreenRoute(it))
            }
        )

    CategoryScreenStateless(
        categoryScreenState = categoryScreenState,
        categoryScreenActions = categoryScreenActions,
    )
}

@Composable
fun CategoryScreenStateless(
    categoryScreenState: CategoryScreenState,
    categoryScreenActions: CategoryScreenActions,
) {
    Column {
        when (categoryScreenState) {
            is CategoryScreenState.Error -> ScreenError(categoryScreenState.errorMessage)
            is CategoryScreenState.Loading -> ScreenLoading()
            is CategoryScreenState.Success -> SuccessCategoryScreen(
                categorySuccessContent = categoryScreenState.categorySuccessContent,
                categoryScreenActions = categoryScreenActions,
            )
        }
    }

}

@Composable
fun SuccessCategoryScreen(
    categorySuccessContent: CategorySuccessContent,
    categoryScreenActions: CategoryScreenActions,
) {
    RedirectToCategoryFormButton(categoryScreenActions)
    NumberOfCategories(categorySuccessContent)
    Divider()
    CategoriesList(
        categorySuccessContent = categorySuccessContent,
        categoryScreenActions = categoryScreenActions,
    )
}

@Composable

fun RedirectToCategoryFormButton(
    categoryScreenActions: CategoryScreenActions,
) {
    Column(modifier = defaultModifier) {
        Button(
            onClick = {
                categoryScreenActions.onAddCategoryAction.invoke()
            },
            modifier = defaultButtonModifier,
        ) {
            Text("Dodaj xDD")
        }

    }
}
