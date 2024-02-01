package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState

@Composable
fun NewCategoryForm(
    refreshScreenFunction: () -> Unit,
    categorySuccessContent: CategorySuccessContent,
    categoryViewModel: CategoryScreenViewModel = hiltViewModel(),
) {

    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }

    val onFormSubmitAction: (String) -> Unit = { newCategory ->
        buttonIsLoading = true
        categoryViewModel.addCategory(
            newCategory,
            ButtonActions(
                onSuccessAction = {
                    buttonIsLoading = false
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
        textFieldLabel = stringResource(R.string.newCategoryName),
        buttonLabel = stringResource(R.string.addCategory),
        categoryNamesOnly = categorySuccessContent.categoryNamesOnly,
    )
}
