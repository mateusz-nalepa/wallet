package com.mateuszcholyn.wallet.ui.screen.addoreditexpense

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.EMPTY_STRING
import com.mateuszcholyn.wallet.util.asFormattedAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

sealed class AddOrEditExpenseScreenState {
    object Loading : AddOrEditExpenseScreenState()
    object Show : AddOrEditExpenseScreenState()
    data class Error(val errorMessage: String) : AddOrEditExpenseScreenState()
}

data class FormDetails(
    val actualExpenseId: Long? = null,
    val amount: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val category: CategoryView? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val submitButtonLabel: String = EMPTY_STRING,
) {
    fun categoryOrThrow(): CategoryView {
        return requireNotNull(category) { "Category should not be null now" }
    }
}


@HiltViewModel
class AddOrEditExpenseViewModel @Inject constructor(
    private val categoryService: CategoryService,
    private val expenseService: ExpenseService,
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
        return categoryService.getAllWithDetailsOrderByUsageDesc().map { it.toCategoryView() }
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
        _addOrEditExpenseFormState.value = _addOrEditExpenseFormState.value.copy(date = newDate)
    }

    fun updateSubmitButtonLabel(newLabel: String) {
        _addOrEditExpenseFormState.value =
            _addOrEditExpenseFormState.value.copy(submitButtonLabel = newLabel)
    }

    fun screenVisible() {
        _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Show
    }

    fun saveExpense() {
        val expense =
            Expense(
                id = addOrEditExpenseFormState.value.actualExpenseId,
                amount = addOrEditExpenseFormState.value.amount.toBigDecimal(),
                description = addOrEditExpenseFormState.value.description,
                category = addOrEditExpenseFormState.value.categoryOrThrow().toExistingCategory(),
                date = addOrEditExpenseFormState.value.date,
            )
        expenseService.saveExpense(expense)
    }

    fun loadExpense(expenseId: Long) {
        _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Loading
        val expense = expenseService.getById(expenseId)
        _addOrEditExpenseFormState.value = _addOrEditExpenseFormState.value.copy(
            actualExpenseId = expense.id,
            amount = expense.amount.asFormattedAmount().toString(),
            description = expense.description,
            category = expense.category.toCategoryView(),
            date = expense.date,
        )
        _addOrEditExpenseScreenState.value = AddOrEditExpenseScreenState.Show
    }

}
