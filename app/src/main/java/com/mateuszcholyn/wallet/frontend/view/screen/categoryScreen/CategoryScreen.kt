package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.categoryFormScreenRoute
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

data class CategoryScreenActions(
    val onRefreshScreenActions: () -> Unit,
    val onAddCategoryAction: () -> Unit,
    val onUpdateCategoryAction: (CategoryId) -> Unit,


    val onCategoryRemoveModalOpen: () -> Unit,
    val onCategoryRemoveModalClose: () -> Unit,
    val onCategoryRemoveAction: (CategoryId) -> Unit,


    val onErrorModalClose: () -> Unit,
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

    val categoryScreenState by remember { categoryScreenViewModel.exportedCategoryScreenState }
    val removeCategoryState by remember { categoryScreenViewModel.exportedRemoveCategoryState }

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
            },
            onCategoryRemoveAction = {
                categoryScreenViewModel.removeCategory(it)
            },
            onErrorModalClose = {
                categoryScreenViewModel.closeErrorModal()
            },
            onCategoryRemoveModalOpen = {
                categoryScreenViewModel.onRemoveCategoryModalOpen()
            },
            onCategoryRemoveModalClose = {
                categoryScreenViewModel.onRemoveCategoryModalClose()
            },
        )

    CategoryScreenStateless(
        removeCategoryState = removeCategoryState,
        categoryScreenState = categoryScreenState,
        categoryScreenActions = categoryScreenActions,
    )
}

@Composable
fun CategoryScreenStateless(
    removeCategoryState: RemoveCategoryState,
    categoryScreenState: CategoryScreenState,
    categoryScreenActions: CategoryScreenActions,
) {


    MyErrorDialogProxy(
        errorModalState = removeCategoryState.errorModalState,
        onErrorModalClose = { categoryScreenActions.onErrorModalClose.invoke() },
    )

    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        when (categoryScreenState) {
            is CategoryScreenState.Error -> ScreenError(categoryScreenState.errorMessageKey)
            is CategoryScreenState.Loading -> ScreenLoading()
            is CategoryScreenState.Success -> SuccessCategoryScreen(
                categorySuccessContent = categoryScreenState.categorySuccessContent,
                categoryScreenActions = categoryScreenActions,
                removeCategoryState = removeCategoryState,
            )
        }
    }

}

@Composable
fun SuccessCategoryScreen(
    categorySuccessContent: CategorySuccessContent,
    categoryScreenActions: CategoryScreenActions,
    removeCategoryState: RemoveCategoryState,
) {
    RedirectToCategoryFormButton(categoryScreenActions)
    NumberOfCategories(categorySuccessContent)
    Divider()
    CategoriesList(
        categorySuccessContent = categorySuccessContent,
        categoryScreenActions = categoryScreenActions,
        removeCategoryState = removeCategoryState,
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
            Text(stringResource(R.string.button_addCategory))
        }

    }
}
