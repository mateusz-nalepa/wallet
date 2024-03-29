package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextFieldV2
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


enum class CategorySubmitButton {
    DISABLED,
    ENABLED,
    LOADING,
}

enum class CategoryScreenMode {
    CATEGORY,
    SUB_CATEGORY,
}

data class CategoryFormUiState(
//    val categoryScreenMode: CategoryScreenMode = CategoryScreenMode.SUB_CATEGORY,
    val categoryScreenMode: CategoryScreenMode = CategoryScreenMode.CATEGORY,
    val categoryName: String = EMPTY_STRING,
    val subCategoryName: String = EMPTY_STRING,
    val isFormInvalid: Boolean = false,
    val submitButtonState: CategorySubmitButton = CategorySubmitButton.DISABLED,
    val showCategoryAlreadyExistsWarning: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
    @StringRes
    val buttonLabelKey: Int = R.string.button_addCategory,
)

data class CategoryFormUiActions(
    val onCategoryValueChanged: (String) -> Unit,
    val onErrorModalClose: () -> Unit,
    val onFormSubmit: () -> Unit,
)


@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryFormStateless(
    categoryFormUiState: CategoryFormUiState,
    categoryFormUiActions: CategoryFormUiActions,
) {
    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        when (categoryFormUiState.categoryScreenMode) {
            CategoryScreenMode.CATEGORY -> {
                ValidatedTextFieldV2(
                    textFieldLabel = stringResource(R.string.common_category),
                    value = categoryFormUiState.categoryName,
                    onValueChange = {
                        categoryFormUiActions.onCategoryValueChanged.invoke(it)
                    },
                    isValueInvalid = categoryFormUiState.isFormInvalid,
                    valueInvalidText = stringResource(R.string.validation_incorrectCategoryName),
                    modifier = defaultModifier,
                )
            }

            CategoryScreenMode.SUB_CATEGORY -> {
                ValidatedTextFieldV2(
                    enabled = false,
                    textFieldLabel = stringResource(R.string.main_category),
                    value = categoryFormUiState.categoryName,
                    onValueChange = {
                        categoryFormUiActions.onCategoryValueChanged.invoke(it)
                    },
                    isValueInvalid = categoryFormUiState.isFormInvalid,
                    valueInvalidText = stringResource(R.string.validation_incorrectCategoryName),
                    modifier = defaultModifier,
                )
                ValidatedTextFieldV2(
                    textFieldLabel = stringResource(R.string.sub_category),
                    value = categoryFormUiState.categoryName,
                    onValueChange = {},
                    isValueInvalid = categoryFormUiState.isFormInvalid,
                    valueInvalidText = stringResource(R.string.validation_incorrectCategoryName),
                    modifier = defaultModifier,
                )
            }
        }

        // chyba bym to wywalił całkowicie XD
//        if (categoryFormUiState.showCategoryAlreadyExistsWarning) {
//            Text(
//                text = stringResource(R.string.validation_warning_category_already_exists),
//                color = MaterialTheme.colors.primary,
//                modifier = defaultModifier,
//            )
//        }

        MyErrorDialogProxy(
            errorModalState = categoryFormUiState.errorModalState,
            onErrorModalClose = categoryFormUiActions.onErrorModalClose
        )

        Button(
            enabled = categoryFormUiState.submitButtonState == CategorySubmitButton.ENABLED,
            onClick = {
                categoryFormUiActions.onFormSubmit.invoke()
            },
            modifier = defaultButtonModifier,
        ) {
            if (categoryFormUiState.submitButtonState == CategorySubmitButton.LOADING) {
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
            } else {
                Text(stringResource(categoryFormUiState.buttonLabelKey))
            }
        }
    }
}
