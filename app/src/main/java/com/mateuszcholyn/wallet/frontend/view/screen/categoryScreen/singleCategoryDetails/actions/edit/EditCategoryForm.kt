package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryFormStateless
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategorySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState


@Composable
fun EditCategoryForm(
    categoryQuickSummary: CategoryQuickSummary,
    actualCategoryName: String,
    onFormSubmitted: () -> Unit,
    categorySuccessContent: CategorySuccessContent,
    refreshScreenFunction: () -> Unit,
    categoryViewModel: CategoryScreenViewModel = hiltViewModel(),
) {


    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }

    val onFormSubmitAction: (String) -> Unit = { newCategoryName ->
        buttonIsLoading = true
        categoryViewModel.updateCategory(
            categoryQuickSummary.copy(categoryName = newCategoryName),
            ButtonActions(
                onSuccessAction = {
                    buttonIsLoading = false
                    onFormSubmitted.invoke()
                    refreshScreenFunction.invoke()
                },
                onErrorAction = {
                    errorState = ErrorModalState.Visible(it)
                    buttonIsLoading = false
                }
            )
        )
    }

    CategoryFormStateless(
        submitButtonIsLoading = buttonIsLoading,
        onFormSubmit = onFormSubmitAction,
        errorModalState = errorState,
        onErrorModalClose = { errorState = ErrorModalState.NotVisible },
        textFieldLabel = stringResource(R.string.updatedCategoryName),
        buttonLabel = stringResource(R.string.update),
        initialCategoryName = actualCategoryName,
        categoryNamesOnly = categorySuccessContent.categoryNamesOnly,
    )
}
