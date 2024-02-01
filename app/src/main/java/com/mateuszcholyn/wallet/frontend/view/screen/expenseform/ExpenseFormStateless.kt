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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ValidatedTextField
import com.mateuszcholyn.wallet.frontend.view.composables.datapicker.OutlinedDatePickerField
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialog
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.time.LocalDateTime


@SuppressLint("UnrememberedMutableState")
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ExpenseFormStateless(
    submitButtonIsLoading: Boolean,
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
    onMissingCategoriesNavigate: () -> Unit,
    categoryNameOptions: List<CategoryView>,
    onFormSubmit: () -> Unit,
    submitButtonLabel: String,
    formState: FormDetails,
    onCategorySelected: (CategoryView) -> Unit,
    onAmountChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateChange: (LocalDateTime) -> Unit,
) {
    if (categoryNameOptions.isEmpty()) {
        ExpenseNoCategoryPresentInfo(onMissingCategoriesNavigate)
        return
    }

    val isFormValid by derivedStateOf {
        formState.amount.isAmountValid()
    }

    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        WalletDropdown(
            dropdownName = stringResource(R.string.category),
            selectedElement = formState.category!!,
            availableElements = categoryNameOptions,
            onItemSelected = { onCategorySelected(it) },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextField(
                textFieldLabel = stringResource(R.string.amount),
                value = formState.amount,
                onValueChange = { onAmountChange(it) },
                isValueInValidFunction = { !it.isAmountValid() },
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
            if (errorModalState is ErrorModalState.Visible) {
                MyErrorDialog(
                    errorMessage = errorModalState.message,
                    onClose = onErrorModalClose
                )
            }
            Button(
                enabled = isFormValid,
                onClick = { onFormSubmit() },
                modifier = defaultButtonModifier,
            ) {
                if (submitButtonIsLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    Text(submitButtonLabel)
                }
            }
        }
    }
}

private fun String.isAmountValid(): Boolean =
    canConvertToBigDecimal()

private fun String.canConvertToBigDecimal(): Boolean =
    kotlin
        .runCatching {
            customToBigDecimal()
            true
        }
        .getOrDefault(false)
