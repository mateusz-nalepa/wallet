package com.mateuszcholyn.wallet.ui.screen.addoreditexpense

import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import com.mateuszcholyn.wallet.ui.composables.ComposeDateTimePicker
import com.mateuszcholyn.wallet.ui.composables.ValidatedTextField
import com.mateuszcholyn.wallet.ui.dropdown.DropdownElement
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.ui.screen.summary.ScreenLoading
import com.mateuszcholyn.wallet.ui.screen.summary.SummaryError
import com.mateuszcholyn.wallet.ui.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.dateutils.toHumanText
import com.mateuszcholyn.wallet.util.dateutils.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

data class CategoryView(
    override val name: String,
    override val nameKey: Int? = null,
    val id: Long? = null,
    val isAllCategories: Boolean = false,
) : DropdownElement

@Composable
fun NewAddOrEditExpenseScreen(
    navController: NavHostController,
    actualExpenseId: Long? = null,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel()
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }

    if (categoryNameOptions.isEmpty()) {
        NoCategoryPresentInfo(navController)
        return
    }

    val editExpenseText = stringResource(R.string.editExpense)
    val addExpenseText = stringResource(R.string.addExpense)

    DisposableEffect(key1 = Unit) {
        if (actualExpenseId == null) {
            addOrEditExpenseViewModel.updateSubmitButtonLabel(addExpenseText)
            addOrEditExpenseViewModel.updateCategory(categoryNameOptions.first())
        } else {
            addOrEditExpenseViewModel.updateSubmitButtonLabel(editExpenseText)
            addOrEditExpenseViewModel.loadExpense(actualExpenseId)
        }
        addOrEditExpenseViewModel.screenVisible()

        onDispose { }
    }

    val screenState by remember { addOrEditExpenseViewModel.addOrEditExpenseScreenState }
    Column {
        when (val screenStateTemp = screenState) {
            is AddOrEditExpenseScreenState.Error -> SummaryError(screenStateTemp.errorMessage)
            is AddOrEditExpenseScreenState.Loading -> ScreenLoading("Loading")
            is AddOrEditExpenseScreenState.Show -> ShowAddOrEditExpenseScreenContent(
                navController = navController,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ShowAddOrEditExpenseScreenContent(
    navController: NavHostController,
    addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel(),
) {
    val categoryNameOptions by remember { mutableStateOf(addOrEditExpenseViewModel.categoryOptions()) }

    val formState by remember { addOrEditExpenseViewModel.addOrEditExpenseFormState }

    val isFormValid by derivedStateOf {
        !formState.amount.isAmountInValid()
    }

    val datePickerDialogState = rememberMaterialDialogState()
    ComposeDateTimePicker(
        dialogState = datePickerDialogState,
        value = formState.date.toHumanText(),
        onValueChange = {
            addOrEditExpenseViewModel.updateDate(it.toLocalDateTime())
        },
    )

    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        WalletDropdown(
            dropdownName = stringResource(R.string.category),
            selectedElement = formState.category,
            availableElements = categoryNameOptions,
            onItemSelected = {
                addOrEditExpenseViewModel.updateCategory(it)
            },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextField(
                textFieldLabel = stringResource(R.string.amount),
                value = formState.amount,
                onValueChange = {
                    addOrEditExpenseViewModel.updateAmount(it)
                },
                isValueInValidFunction = { it.isAmountInValid() },
                valueInvalidText = stringResource(R.string.incorrectAmount),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            modifier = defaultModifier,
        ) {
            OutlinedTextField(
                value = formState.description,
                onValueChange = {
                    addOrEditExpenseViewModel.updateDescription(it)
                },
                label = { Text(stringResource(R.string.description)) },
                modifier = defaultModifier,
                maxLines = 5,
            )
        }
        Row(
            modifier = defaultModifier,
        ) {
            OutlinedTextField(
                value = formState.date.toHumanText(),
                onValueChange = {},
                label = { Text(stringResource(R.string.date)) },
                modifier = defaultModifier.clickable {
                    datePickerDialogState.show()
                },
                enabled = false,
            )
        }
        Row(modifier = defaultModifier) {
            Button(
                enabled = isFormValid,
                onClick = {
                    addOrEditExpenseViewModel.saveExpense()
                    navController.navigate(NavDrawerItem.SummaryScreen.route)
                },
                modifier = defaultButtonModifier,
            ) {
                Text(formState.submitButtonLabel)
            }
        }
    }
}


fun CategoryDetails.toCategoryView(): CategoryView =
    CategoryView(
        id = id,
        name = name,
    )

fun ExistingCategory.toCategoryView(): CategoryView =
    CategoryView(
        id = id,
        name = name,
    )

fun CategoryView.toExistingCategory(): ExistingCategory =
    ExistingCategory(
        id = id!!,
        name = name,
    )
