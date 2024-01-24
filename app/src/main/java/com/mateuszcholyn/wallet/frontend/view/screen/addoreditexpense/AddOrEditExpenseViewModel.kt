package com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.summary.toCategoryView
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asFormattedAmount
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

sealed class AddOrEditExpenseScreenState {
    object Loading : AddOrEditExpenseScreenState()
    object Show : AddOrEditExpenseScreenState()
    data class Error(val errorMessage: String) : AddOrEditExpenseScreenState()
}

data class FormDetails(
    val actualExpenseId: String? = null,
    val amount: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val category: CategoryView = CategoryView(
        "Wszystkie kategorie",
        R.string.allExpenses
    ), // HODOR: this should be nullable? XD
    val paidAt: LocalDateTime = LocalDateTime.now(),
    val submitButtonLabel: String = EMPTY_STRING,
)


@HiltViewModel
class AddOrEditExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() {

    private var _addOrEditExpenseScreenState: MutableState<AddOrEditExpenseScreenState> =
        mutableStateOf(AddOrEditExpenseScreenState.Loading)
    val addOrEditExpenseScreenState: State<AddOrEditExpenseScreenState>
        get() = _addOrEditExpenseScreenState


    private var _addOrEditExpenseFormState: MutableState<FormDetails> =
        mutableStateOf(FormDetails())
    val addOrEditExpenseFormState: State<FormDetails>
        get() = _addOrEditExpenseFormState


    fun categoryOptions(): List<CategoryView> {
        return getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }
    }

    fun updateCategory(newCategory: CategoryView) {
        _addOrEditExpenseFormState.value =
            _addOrEditExpenseFormState.value.copy(category = newCategory)
    }

    fun updateAmount(newAmount: String) {
        _addOrEditExpenseFormState.value = _addOrEditExpenseFormState.value.copy(amount = newAmount)
    }

    fun updateDescription(newDescription: String) {
        _addOrEditExpenseFormState.value =
            _addOrEditExpenseFormState.value.copy(description = newDescription)
    }

    fun updateDate(newDate: LocalDateTime) {
        _addOrEditExpenseFormState.value = _addOrEditExpenseFormState.value.copy(paidAt = newDate)
    }

    fun setDateToToday() {
        updateDate(LocalDateTime.now())
    }

    fun updateSubmitButtonLabel(newLabel: String) {
        _addOrEditExpenseFormState.value =
            _addOrEditExpenseFormState.value.copy(submitButtonLabel = newLabel)
    }

    fun screenVisible() {
        _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Show
    }

    fun copyExpense() {
        val addExpenseParameters =
            AddExpenseParameters(
                amount = addOrEditExpenseFormState.value.amount.toBigDecimal(),
                description = addOrEditExpenseFormState.value.description,
                paidAt = addOrEditExpenseFormState.value.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                categoryId = CategoryId(addOrEditExpenseFormState.value.category.categoryId!!)
            )
        addExpenseUseCase.invoke(addExpenseParameters)
    }

    fun saveExpense() {
        val actualExpenseId = addOrEditExpenseFormState.value.actualExpenseId


        if (actualExpenseId == null) {

            val addExpenseParameters =
                AddExpenseParameters(
                    amount = addOrEditExpenseFormState.value.amount.toBigDecimal(),
                    description = addOrEditExpenseFormState.value.description,
                    paidAt = addOrEditExpenseFormState.value.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                    categoryId = CategoryId(addOrEditExpenseFormState.value.category.categoryId!!)
                )
            addExpenseUseCase.invoke(addExpenseParameters)
        } else {
            val updatedExpense =
                ExpenseV2(
                    expenseId = ExpenseId(actualExpenseId),
                    amount = addOrEditExpenseFormState.value.amount.toBigDecimal(),
                    description = addOrEditExpenseFormState.value.description,
                    categoryId = CategoryId(addOrEditExpenseFormState.value.category.categoryId!!),
                    paidAt = addOrEditExpenseFormState.value.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                )
            updateExpenseUseCase.invoke(updatedExpense)
        }
    }

    fun loadExpense(expenseId: String) {
        viewModelScope.launch {
            try {
                _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Loading
                val expense = getExpenseUseCase.invoke(ExpenseId(expenseId))

                _addOrEditExpenseFormState.value = _addOrEditExpenseFormState.value.copy(
                    actualExpenseId = expense.expenseId.id,
                    amount = expense.amount.asFormattedAmount().toString(),
                    description = expense.description,
                    category = expense.toCategoryView(),
                    paidAt = expense.paidAt.fromUTCInstantToUserLocalTimeZone(),
                )
                _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Show
            } catch (t: Throwable) {
                Log.d("BK", "Exception: ${t.message}")
                _addOrEditExpenseScreenState.value =
                    AddOrEditExpenseScreenState.Error(t.message ?: "Unknown error sad times")

            }
        }
    }

}

fun ExpenseV2WithCategory.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )
