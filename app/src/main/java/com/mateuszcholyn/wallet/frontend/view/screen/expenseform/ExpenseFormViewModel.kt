package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.frontend.di.usecases.LocalDateTimeProvider
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.history.toCategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.util.PriceFormatterParameters
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmountWithoutCurrencySymbol
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

sealed interface ExpenseScreenMode {
    data object Add : ExpenseScreenMode
    data class Update(val expenseId: ExpenseId) : ExpenseScreenMode
    data class Copy(val expenseId: ExpenseId) : ExpenseScreenMode
}


@HiltViewModel
class ExpenseFormViewModel @Inject constructor(
    private val localDateTimeProvider: LocalDateTimeProvider,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() { // done tests XD

    private lateinit var onButtonSubmittedAction: () -> Unit
    private var expenseScreenMode: ExpenseScreenMode = ExpenseScreenMode.Add
    private lateinit var priceFormatterParameters: PriceFormatterParameters

    var exportedExpenseFormScreenState =
        mutableStateOf<ExpenseFormScreenState>(ExpenseFormScreenState.Loading)
        private set
    private var expenseFormScreenState by MutableStateDelegate(exportedExpenseFormScreenState)

    var exportedExpenseFormDetailsUiState = mutableStateOf(ExpenseFormDetailsUiState())
        private set
    private var expenseFormDetailsUiState by MutableStateDelegate(exportedExpenseFormDetailsUiState)


    fun initExpenseFormScreen(
        priceFormatterParameters: PriceFormatterParameters,
        actualExpenseId: String?,
        screenMode: String?,
        onButtonSubmittedAction: () -> Unit,
    ) {
        this.onButtonSubmittedAction = onButtonSubmittedAction
        this.priceFormatterParameters = priceFormatterParameters
        viewModelScope.launch {
            try {
                val categories =
                    getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }

                when {
                    categories.isEmpty() -> {
                        expenseFormScreenState = ExpenseFormScreenState.NoCategories
                    }

                    actualExpenseId == null && screenMode == null -> {
                        initScreenForAddMode(
                            categories = categories,
                        )
                    }

                    screenMode == "copy" && actualExpenseId != null -> {
                        initScreenForCopyMode(
                            actualExpenseId = ExpenseId(actualExpenseId),
                            categories = categories,
                        )
                    }

                    actualExpenseId != null -> {
                        initScreenForUpdateMode(
                            actualExpenseId = ExpenseId(actualExpenseId),
                            categories = categories,
                        )
                    }
                }
            } catch (t: Throwable) {
                expenseFormScreenState = ExpenseFormScreenState.Error("error on init XD")
            }
        }
    }

    private fun initScreenForAddMode(
        categories: List<CategoryView>,
    ) {
        expenseScreenMode = ExpenseScreenMode.Add
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
            submitButtonLabelKey = R.string.button_addExpense,
            categories = categories,
            selectedCategory = categories.first(),
        )
        setDateToToday()
        expenseFormScreenState = ExpenseFormScreenState.Show
    }

    private suspend fun initScreenForUpdateMode(
        actualExpenseId: ExpenseId,
        categories: List<CategoryView>,
    ) {
        expenseScreenMode = ExpenseScreenMode.Update(actualExpenseId)
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
            submitButtonLabelKey = R.string.button_editExpense,
            categories = categories,
            expenseSubmitButtonState = ExpenseSubmitButtonState.ENABLED,
        )
        loadExpenseFromDatabase(actualExpenseId)
        expenseFormScreenState = ExpenseFormScreenState.Show
    }

    private suspend fun initScreenForCopyMode(
        actualExpenseId: ExpenseId,
        categories: List<CategoryView>,
    ) {
        expenseScreenMode = ExpenseScreenMode.Copy(actualExpenseId)

        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
            submitButtonLabelKey = R.string.button_copyExpense,
            categories = categories,
            expenseSubmitButtonState = ExpenseSubmitButtonState.ENABLED,
        )
        setDateToToday()
        loadExpenseFromDatabase(actualExpenseId)
        setDateToToday()
        expenseFormScreenState = ExpenseFormScreenState.Show
    }


    private suspend fun loadExpenseFromDatabase(expenseId: ExpenseId) {
        val existingExpense = getExpenseUseCase.invoke(expenseId)
        expenseFormDetailsUiState =
            expenseFormDetailsUiState.copy(
                actualExpenseId = existingExpense.expenseId.id,
                amount = existingExpense.amount.asPrintableAmountWithoutCurrencySymbol(
                    priceFormatterParameters
                ),
                description = existingExpense.description,
                selectedCategory = existingExpense.toCategoryView(),
                paidAt = existingExpense.paidAt.fromUTCInstantToUserLocalTimeZone(),
            )
    }

    fun closeErrorModal() {
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
            errorModalState = ErrorModalState.NotVisible
        )
    }

    private fun markSubmitButtonState() {
        expenseFormDetailsUiState =
            if (isFormValid()) {
                expenseFormDetailsUiState.copy(expenseSubmitButtonState = ExpenseSubmitButtonState.DISABLED)
            } else {
                expenseFormDetailsUiState.copy(expenseSubmitButtonState = ExpenseSubmitButtonState.ENABLED)
            }
    }

    private fun isFormValid(): Boolean =
        expenseFormDetailsUiState.isAmountInvalid

    fun updateCategory(newCategory: CategoryView) {
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(selectedCategory = newCategory)
    }

    fun updateAmount(newAmount: String) {
        val isAmountValid = newAmount.isAmountInvalid()

        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
            amount = newAmount,
            isAmountInvalid = isAmountValid,
        )
        markSubmitButtonState()
    }

    fun updateDescription(newDescription: String) {
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(description = newDescription)
    }

    fun updateDate(newDate: LocalDateTime) {
        expenseFormDetailsUiState = expenseFormDetailsUiState.copy(paidAt = newDate)
    }

    private fun setDateToToday() {
        updateDate(localDateTimeProvider.now())
    }

    fun saveExpense() {
        when (expenseScreenMode) {
            ExpenseScreenMode.Add -> {
                addNewExpense(R.string.error_unable_to_addExpense)
            }

            is ExpenseScreenMode.Update -> {
                updateExpense()
            }

            is ExpenseScreenMode.Copy -> {
                addNewExpense(R.string.error_unable_to_copyExpense)
            }
        }
    }

    private fun addNewExpense(
        @StringRes
        errorMessageKey: Int,
    ) {
        genericSaveExpense(errorMessageKey) {
            val addExpenseParameters =
                AddExpenseParameters(
                    amount = expenseFormDetailsUiState.amount.stringToBigDecimal(),
                    description = expenseFormDetailsUiState.description,
                    paidAt = expenseFormDetailsUiState.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                    categoryId = CategoryId(expenseFormDetailsUiState.selectedCategory?.categoryId!!)
                )
            addExpenseUseCase.invoke(addExpenseParameters)
        }
    }

    private fun updateExpense() {
        genericSaveExpense(R.string.error_unable_to_updateExpense) {
            val updatedExpense =
                Expense(
                    expenseId = (expenseScreenMode as ExpenseScreenMode.Update).expenseId,
                    amount = expenseFormDetailsUiState.amount.stringToBigDecimal(),
                    description = expenseFormDetailsUiState.description,
                    categoryId = CategoryId(expenseFormDetailsUiState.selectedCategory?.categoryId!!),
                    paidAt = expenseFormDetailsUiState.paidAt.fromUserLocalTimeZoneToUTCInstant(),
                )
            updateExpenseUseCase.invoke(updatedExpense)
        }
    }

    private fun genericSaveExpense(
        @StringRes
        errorMessageKey: Int,
        buttonAction: suspend () -> Unit,
    ) {
        viewModelScope.launch { // DONE
            try {
                expenseFormDetailsUiState =
                    expenseFormDetailsUiState.copy(expenseSubmitButtonState = ExpenseSubmitButtonState.LOADING)
                buttonAction.invoke()
                onButtonSubmittedAction.invoke()
            } catch (t: Throwable) {
                println(t)
                expenseFormDetailsUiState = expenseFormDetailsUiState.copy(
                    errorModalState = ErrorModalState.Visible(errorMessageKey),
                    expenseSubmitButtonState = ExpenseSubmitButtonState.ENABLED,
                )
            }
        }
    }

}

private fun ExpenseWithCategory.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )


private val AMOUNT_WITH_OPTIONAL_TWO_PLACES_AFTER_SEPARATOR_REGEX: Regex =
    "^(\\d*)(,|.)?(\\d{0,2})\$".toRegex()

private fun String.isAmountInvalid(): Boolean =
    !this.isAmountValid()

fun String.isAmountValid(): Boolean =
    runCatching {
        this.stringToBigDecimal()
            .toString()
            .matches(AMOUNT_WITH_OPTIONAL_TWO_PLACES_AFTER_SEPARATOR_REGEX)
    }
        .getOrDefault(false)

// FIXME: what, if there is other amount separator than "," and "."? :D
fun String.stringToBigDecimal(): BigDecimal =
    this.replace(",", ".").toBigDecimal()
