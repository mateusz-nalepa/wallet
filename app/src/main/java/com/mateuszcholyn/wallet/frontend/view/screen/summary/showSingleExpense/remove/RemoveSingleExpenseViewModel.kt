package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoveSingleExpenseViewModel @Inject constructor(
    private val removeExpenseUseCase: RemoveExpenseUseCase,
) : ViewModel() {

    fun removeExpenseById(
        expenseId: ExpenseId,
        onSuccessAction: () -> Unit,
        onErrorAction: (String) -> Unit,
    ) {
        viewModelScope.launch { // DONE
            try {
                removeExpenseUseCase.invoke(expenseId)
                onSuccessAction.invoke()
            } catch (t: Throwable) {
                onErrorAction.invoke("Nie udalo sie usunac wydatku")
            }
        }
    }
}