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
fun CopyExpenseScreen(
    onMissingCategoriesNavigate: () -> Unit,
    onFormSubmitNavigateAction: () -> Unit,
    actualExpenseId: String,
    expenseFormViewModel: ExpenseFormViewModel = hiltViewModel(),
) {

    DisposableEffect(key1 = Unit, effect = {
        expenseFormViewModel.setDateToToday()
        expenseFormViewModel.initExpenseScreen(actualExpenseId)
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
                    expenseFormViewModel.addCopiedExpense()
                    onFormSubmitNavigateAction()
                },
                submitButtonLabel = stringResource(R.string.copyExpense),
                formState = formState,
                onCategorySelected = { expenseFormViewModel.updateCategory(it) },
                onAmountChange = { expenseFormViewModel.updateAmount(it) },
                onDescriptionChange = { expenseFormViewModel.updateDescription(it) },
                onDateChange = { expenseFormViewModel.updateDate(it) },
            )
        }
    }

}
