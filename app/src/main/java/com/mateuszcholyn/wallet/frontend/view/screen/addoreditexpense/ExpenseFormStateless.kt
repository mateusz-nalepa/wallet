package com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextField
import com.mateuszcholyn.wallet.frontend.view.composables.datapicker.OutlinedDatePickerField
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.time.LocalDateTime

@Composable
fun CopyExpenseScreen(
    onFormSubmitNavigateAction: () -> Unit,
    actualExpenseId: String,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel(),
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }

    DisposableEffect(key1 = Unit, effect = {
        addOrEditExpenseViewModel.loadExpense(actualExpenseId)
        addOrEditExpenseViewModel.setDateToToday()
        addOrEditExpenseViewModel.screenVisible()
        onDispose { }
    })

    val formState by remember { addOrEditExpenseViewModel.addOrEditExpenseFormState }

    ExpenseFormStateless(
        categoryNameOptions = categoryNameOptions,
        onFormSubmit = {
            addOrEditExpenseViewModel.copyExpense()
            onFormSubmitNavigateAction()
        },
        submitButtonLabel = stringResource(R.string.copyExpense),
        formState = formState,
        onCategorySelected = { addOrEditExpenseViewModel.updateCategory(it) },
        onAmountChange = { addOrEditExpenseViewModel.updateAmount(it) },
        onDescriptionChange = { addOrEditExpenseViewModel.updateDescription(it) },
        onDateChange = { addOrEditExpenseViewModel.updateDate(it) },
    )
}


@Composable
fun EditExpenseScreen(
    onFormSubmitNavigateAction: () -> Unit,
    actualExpenseId: String,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel(),
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }

    DisposableEffect(key1 = Unit, effect = {
        addOrEditExpenseViewModel.loadExpense(actualExpenseId)
        addOrEditExpenseViewModel.screenVisible()
        onDispose { }
    })

    val formState by remember { addOrEditExpenseViewModel.addOrEditExpenseFormState }

    ExpenseFormStateless(
        categoryNameOptions = categoryNameOptions,
        onFormSubmit = {
            addOrEditExpenseViewModel.saveExpense()
            onFormSubmitNavigateAction()
        },
        submitButtonLabel = stringResource(R.string.editExpense),
        formState = formState,
        onCategorySelected = { addOrEditExpenseViewModel.updateCategory(it) },
        onAmountChange = { addOrEditExpenseViewModel.updateAmount(it) },
        onDescriptionChange = { addOrEditExpenseViewModel.updateDescription(it) },
        onDateChange = { addOrEditExpenseViewModel.updateDate(it) },
    )
}


@Composable
fun AddExpenseScreen(
    onFormSubmitNavigateAction: () -> Unit,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel(),
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }

    DisposableEffect(key1 = Unit, effect = {
        addOrEditExpenseViewModel.updateCategory(categoryNameOptions.first())
        addOrEditExpenseViewModel.screenVisible()
        onDispose { }
    })

    val formState by remember { addOrEditExpenseViewModel.addOrEditExpenseFormState }

    ExpenseFormStateless(
        categoryNameOptions = categoryNameOptions,
        onFormSubmit = {
            addOrEditExpenseViewModel.saveExpense()
            onFormSubmitNavigateAction()
        },
        submitButtonLabel = stringResource(R.string.addExpense),
        formState = formState,
        onCategorySelected = { addOrEditExpenseViewModel.updateCategory(it) },
        onAmountChange = { addOrEditExpenseViewModel.updateAmount(it) },
        onDescriptionChange = { addOrEditExpenseViewModel.updateDescription(it) },
        onDateChange = { addOrEditExpenseViewModel.updateDate(it) },
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ExpenseFormStateless(
    categoryNameOptions: List<CategoryView>,
    onFormSubmit: () -> Unit,
    submitButtonLabel: String,
    formState: FormDetails,
    onCategorySelected: (CategoryView) -> Unit,
    onAmountChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateChange: (LocalDateTime) -> Unit,
) {

    val isFormValid by derivedStateOf {
        !formState.amount.isAmountInValid()
    }

    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        WalletDropdown(
            dropdownName = stringResource(R.string.category),
            selectedElement = formState.category,
            availableElements = categoryNameOptions,
            onItemSelected = { onCategorySelected(it) },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextField(
                textFieldLabel = stringResource(R.string.amount),
                value = formState.amount,
                onValueChange = { onAmountChange(it) },
                isValueInValidFunction = { it.isAmountInValid() },
                valueInvalidText = stringResource(R.string.incorrectAmount),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(modifier = defaultModifier) {
            OutlinedTextField(
                value = formState.description,
                onValueChange = { onDescriptionChange(it) },
                label = { Text(stringResource(R.string.description)) },
                modifier = defaultModifier,
                maxLines = 5,
            )
        }
        Row(modifier = defaultModifier) {
            OutlinedDatePickerField(
                value = formState.paidAt,
                onValueChange = { onDateChange(it) },
            )
        }
        Row(modifier = defaultModifier) {
            Button(
                enabled = isFormValid,
                onClick = { onFormSubmit() },
                modifier = defaultButtonModifier,
            ) {
                Text(submitButtonLabel)
            }
        }
    }
}
