package com.mateuszcholyn.wallet.frontend.view.screen.categoryForm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryFormStateless
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryFormUiActions
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryFormUiState
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem

@Composable
fun CategoryFormScreen(
    existingCategoryId: String? = null,
    navHostController: NavHostController,
    categoryScreenFormViewModel: CategoryScreenFormViewModel = hiltViewModel(),
) {

    DisposableEffect(key1 = Unit, effect = {
        categoryScreenFormViewModel.initCategoryFormScreen(
            existingCategoryId = existingCategoryId,
            onButtonSubmittedAction = {
                navHostController.navigate(NavDrawerItem.Categories.route)
            }
        )
        onDispose { }
    })


    val categoryScreenFormState by remember { categoryScreenFormViewModel.exportedCategoryScreenFormState }
    val categoryFormUiState by remember { categoryScreenFormViewModel.exportedCategoryFormUiState }

    val categoryFormUiActions =
        CategoryFormUiActions(
            onCategoryValueChanged = {
                categoryScreenFormViewModel.updateCategoryFormName(it)
            },
            onErrorModalClose = {
                categoryScreenFormViewModel.closeErrorModal()
            },
            onFormSubmit = {
                categoryScreenFormViewModel.saveCategory()
            },
        )

    CategoryFormScreenStateless(
        categoryScreenFormState = categoryScreenFormState,
        categoryFormUiState = categoryFormUiState,
        categoryFormUiActions = categoryFormUiActions,
    )
}


@Composable
fun CategoryFormScreenStateless(
    categoryScreenFormState: CategoryScreenFormState,
    categoryFormUiState: CategoryFormUiState,
    categoryFormUiActions: CategoryFormUiActions,
) {
    when (categoryScreenFormState) {
        CategoryScreenFormState.Loading -> ScreenLoading()
        is CategoryScreenFormState.Error -> ScreenError(categoryScreenFormState.errorMessageKey)
        is CategoryScreenFormState.Success -> {
            CategoryFormStateless(
                categoryFormUiState = categoryFormUiState,
                categoryFormUiActions = categoryFormUiActions,
            )
        }
    }

}

