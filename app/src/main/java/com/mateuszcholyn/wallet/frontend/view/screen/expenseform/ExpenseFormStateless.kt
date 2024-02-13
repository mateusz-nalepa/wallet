package com.mateuszcholyn.wallet.frontend.view.screen.expenseform


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextFieldV2
import com.mateuszcholyn.wallet.frontend.view.composables.datapicker.OutlinedDatePickerField
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.categoryFormScreenRoute
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.time.LocalDateTime

sealed class ExpenseFormScreenState {
    data object Loading : ExpenseFormScreenState()
    data object Show : ExpenseFormScreenState()

    data object NoCategories : ExpenseFormScreenState()
    data class Error(val errorMessage: String) : ExpenseFormScreenState()
}

data class ExpenseFormActions(
    val onCategorySelected: (CategoryView) -> Unit,
    val onAmountChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
    val onDateChange: (LocalDateTime) -> Unit,
    val onErrorModalClose: () -> Unit,
    val onFormSubmit: () -> Unit,
    val onMissingCategoriesNavigate: () -> Unit,
)

enum class ExpenseSubmitButtonState {
    DISABLED,
    ENABLED,
    LOADING,
}

data class ExpenseFormDetailsUiState(
    val categories: List<CategoryView> = emptyList(),

    val actualExpenseId: String? = null,
    // ehh, dodałbyś jakaś walidację na tą sumę XD
    val amount: String = EMPTY_STRING,
    val isAmountInvalid: Boolean = false,
    val description: String = EMPTY_STRING,
    val selectedCategory: CategoryView? = null,
    val paidAt: LocalDateTime = LocalDateTime.now(),

    val submitButtonLabel: String = EMPTY_STRING,
    val expenseSubmitButtonState: ExpenseSubmitButtonState = ExpenseSubmitButtonState.DISABLED,

    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)


@Composable
fun ExpenseFormScreen(
    navHostController: NavHostController,
    actualExpenseId: String? = null,
    screenMode: String? = null,
    expenseFormViewModel: ExpenseFormViewModel = hiltViewModel(),
) {
    DisposableEffect(key1 = Unit, effect = {
        expenseFormViewModel.initExpenseFormScreen(
            actualExpenseId = actualExpenseId,
            screenMode = screenMode,
            onButtonSubmittedAction = {
                navHostController.navigate(NavDrawerItem.SummaryScreen.route)
            },
        )
        onDispose { }
    })


    val expenseScreenFormState by remember { expenseFormViewModel.exportedExpenseFormScreenState }
    val expenseFormDetailsUiState by remember { expenseFormViewModel.exportedExpenseFormDetailsUiState }

    val expenseFormActions =
        ExpenseFormActions(
            onCategorySelected = {
                expenseFormViewModel.updateCategory(it)
            },
            onAmountChange = {
                expenseFormViewModel.updateAmount(it)
            },
            onDescriptionChange = {
                expenseFormViewModel.updateDescription(it)

            },
            onDateChange = {
                expenseFormViewModel.updateDate(it)

            },
            onErrorModalClose = {
                expenseFormViewModel.closeErrorModal()

            },
            onFormSubmit = {
                expenseFormViewModel.saveExpense()

            },
            onMissingCategoriesNavigate = {
                navHostController.navigate(categoryFormScreenRoute())
            },
        )

    ExpenseFormStateless(
        expenseFormScreenState = expenseScreenFormState,
        expenseFormActions = expenseFormActions,
        expenseFormDetailsUiState = expenseFormDetailsUiState,
    )
}

@Composable
fun ExpenseFormStateless(
    expenseFormScreenState: ExpenseFormScreenState,
    expenseFormActions: ExpenseFormActions,
    expenseFormDetailsUiState: ExpenseFormDetailsUiState,
) {

    MyErrorDialogProxy(
        errorModalState = expenseFormDetailsUiState.errorModalState,
        onErrorModalClose = expenseFormActions.onErrorModalClose,
    )

    when (expenseFormScreenState) {
        is ExpenseFormScreenState.Error -> ScreenError("nie nie pokazę forma XD")
        ExpenseFormScreenState.Loading -> ScreenLoading()
        ExpenseFormScreenState.NoCategories -> {
            ExpenseNoCategoryPresentInfoStateless(expenseFormActions.onMissingCategoriesNavigate)
        }

        is ExpenseFormScreenState.Show -> {
            ShowExpenseFormStateless(
                expenseFormActions = expenseFormActions,
                categoryNameOptions = expenseFormDetailsUiState.categories,
                formState = expenseFormDetailsUiState,
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ShowExpenseFormStateless(
    expenseFormActions: ExpenseFormActions,
    categoryNameOptions: List<CategoryView>,
    formState: ExpenseFormDetailsUiState,
) {

    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        WalletDropdown(
            dropdownName = stringResource(R.string.category),
            selectedElement = formState.selectedCategory!!,
            availableElements = categoryNameOptions,
            onItemSelected = { expenseFormActions.onCategorySelected(it) },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextFieldV2(
                textFieldLabel = stringResource(R.string.amount),
                value = formState.amount,
                onValueChange = { expenseFormActions.onAmountChange(it) },
                isValueInvalid = formState.isAmountInvalid,
                valueInvalidText = stringResource(R.string.incorrectAmount),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(modifier = defaultModifier) {
            OutlinedTextField(
                value = formState.description,
                onValueChange = { expenseFormActions.onDescriptionChange(it) },
                label = { Text(stringResource(R.string.description)) },
                modifier = defaultModifier,
                maxLines = 5,
            )
        }
        Row(modifier = defaultModifier) {
            OutlinedDatePickerField(
                value = formState.paidAt,
                onValueChange = { expenseFormActions.onDateChange(it) },
            )
        }
        Row(modifier = defaultModifier) {
            Button(
                enabled = formState.expenseSubmitButtonState == ExpenseSubmitButtonState.ENABLED,
                onClick = { expenseFormActions.onFormSubmit() },
                modifier = defaultButtonModifier,
            ) {
                if (formState.expenseSubmitButtonState == ExpenseSubmitButtonState.LOADING) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    Text(formState.submitButtonLabel)
                }
            }
        }
    }
}
