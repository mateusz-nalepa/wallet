package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun AddExpenseScreen(
    onMissingCategoriesNavigate: () -> Unit,
    onFormSubmitNavigateAction: () -> Unit,
    expenseFormViewModel: ExpenseFormViewModel = hiltViewModel(),
) {

    DisposableEffect(key1 = Unit, effect = {
        expenseFormViewModel.initExpenseScreen()
        onDispose { }
    })

    val categoryNameOptions by remember { expenseFormViewModel.categoryOptions }
    val formState by remember { expenseFormViewModel.expenseFormState }
    val expenseScreenState by remember { expenseFormViewModel.expenseScreenState }

    when (expenseScreenState) {
        is ExpenseFormScreenState.Error -> {
            ScreenError(errorMsg = "error jak chcę zrobić kopię")
        }

        is ExpenseFormScreenState.Loading -> {
            ScreenLoading()
        }

        is ExpenseFormScreenState.Show -> {
            ExpenseFormStateless(
                onMissingCategoriesNavigate = onMissingCategoriesNavigate,
                categoryNameOptions = categoryNameOptions,
                onFormSubmit = {
                    expenseFormViewModel.addNewExpense()
                    onFormSubmitNavigateAction()
                },
                submitButtonLabel = stringResource(R.string.addExpense),
                formState = formState,
                onCategorySelected = { expenseFormViewModel.updateCategory(it) },
                onAmountChange = { expenseFormViewModel.updateAmount(it) },
                onDescriptionChange = { expenseFormViewModel.updateDescription(it) },
                onDateChange = { expenseFormViewModel.updateDate(it) },
            )
        }
    }


}
