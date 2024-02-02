package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.summary.toCategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asFormattedAmount
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

sealed class ExpenseFormScreenState {
    data object Loading : ExpenseFormScreenState()
    data object Show : ExpenseFormScreenState()
    data class Error(val errorMessage: String) : ExpenseFormScreenState()
}

data class FormDetails(
    val actualExpenseId: String? = null,
    val amount: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val category: CategoryView? = null,
    val paidAt: LocalDateTime = LocalDateTime.now(),
    val submitButtonLabel: String = EMPTY_STRING,
)


@HiltViewModel
class ExpenseFormViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() {

    private var _expenseScreenState: MutableState<ExpenseFormScreenState> =
        mutableStateOf(ExpenseFormScreenState.Loading)
    val expenseScreenState: State<ExpenseFormScreenState>
        get() = _expenseScreenState

    private var _expenseFormState: MutableState<FormDetails> =
        mutableStateOf(FormDetails())

    val expenseFormState: State<FormDetails>
        get() = _expenseFormState

    private var _categoryOptions: MutableState<List<CategoryView>> =
        mutableStateOf(emptyList())

    val categoryOptions: State<List<CategoryView>> =
        _categoryOptions

    fun updateCategory(newCategory: CategoryView) {
        _expenseFormState.value =
            _expenseFormState.value.copy(category = newCategory)
    }

    fun updateAmount(newAmount: String) {
        _expenseFormState.value = _expenseFormState.value.copy(amount = newAmount)
    }

    fun updateDescription(newDescription: String) {
        _expenseFormState.value =
            _expenseFormState.value.copy(description = newDescription)
    }

    fun updateDate(newDate: LocalDateTime) {
        _expenseFormState.value = _expenseFormState.value.copy(paidAt = newDate)
    }

    fun setDateToToday() {
        updateDate(LocalDateTime.now())
    }

    fun copyExpense(buttonActions: ButtonActions) {
        genericSaveExpense(
            buttonActions,
            "Error podczas kopiowania",
        )
    }

    fun addNewExpense(
        buttonActions: ButtonActions,
    ) {
        genericSaveExpense(
            buttonActions,
            "Error podczas dodawania",
        )
    }

    fun updateExpense(
        buttonActions: ButtonActions,
    ) {
        genericSaveExpense(
            buttonActions,
            "Error podczas aktualizacji",
        ) {
            expenseFormState.value.actualExpenseId
                ?: throw RuntimeException("Cannot update. Missing id!")
        }
    }

    private fun genericSaveExpense(
        buttonActions: ButtonActions,
        errorMessage: String,
        expenseIdProvider: () -> String? = { null },
    ) {
        viewModelScope.launch { // DONE
            try {
                val expenseId = expenseIdProvider.invoke()
                if (expenseId == null) {
                    addExpense()
                } else {
                    updateExpense(expenseId)
                }
                buttonActions.onSuccessAction.invoke()
            } catch (t: Throwable) {
                println(t)
                buttonActions.onErrorAction.invoke(errorMessage)
            }
        }
    }

    private suspend fun addExpense() {
        val addExpenseParameters =
            AddExpenseParameters(
                amount = expenseFormState.value.amount.customToBigDecimal(),
                description = expenseFormState.value.description,
                paidAt = expenseFormState.value.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                categoryId = CategoryId(expenseFormState.value.category?.categoryId!!)
            )
        addExpenseUseCase.invoke(addExpenseParameters)
    }

    private suspend fun updateExpense(actualExpenseId: String) {
        val updatedExpense =
            Expense(
                expenseId = ExpenseId(actualExpenseId),
                amount = expenseFormState.value.amount.customToBigDecimal(),
                description = expenseFormState.value.description,
                categoryId = CategoryId(expenseFormState.value.category?.categoryId!!),
                paidAt = expenseFormState.value.paidAt.fromUserLocalTimeZoneToUTCInstant(),
            )
        updateExpenseUseCase.invoke(updatedExpense)
    }


    fun initExpenseScreen(expenseId: String? = null) {
        viewModelScope.launch { // DONE
            try {
                _expenseScreenState.value = ExpenseFormScreenState.Loading
                _categoryOptions.value = getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }
                updateCategory(_categoryOptions.value.first())
                if (expenseId != null) {
                    updateExpenseFormFromDatabase(expenseId)
                }
                _expenseScreenState.value = ExpenseFormScreenState.Show
            } catch (t: Throwable) {
                Log.d("BK", "Exception: ${t.message}")
                _expenseScreenState.value =
                    ExpenseFormScreenState.Error(t.message ?: "Unknown error sad times")
            }
        }
    }

    private suspend fun updateExpenseFormFromDatabase(expenseId: String) {
        val expense = getExpenseUseCase.invoke(ExpenseId(expenseId))
        _expenseFormState.value =
            _expenseFormState.value.copy(
                actualExpenseId = expense.expenseId.id,
                amount = expense.amount.asFormattedAmount().toString(),
                description = expense.description,
                category = expense.toCategoryView(),
                paidAt = expense.paidAt.fromUTCInstantToUserLocalTimeZone(),
            )
    }

}

private fun ExpenseWithCategory.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )

fun String.customToBigDecimal(): BigDecimal =
    this.replace(",", ".").toBigDecimal()