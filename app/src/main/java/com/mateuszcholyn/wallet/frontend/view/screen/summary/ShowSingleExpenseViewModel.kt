package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowSingleExpenseViewModel @Inject constructor(
    private val removeExpenseUseCase: RemoveExpenseUseCase,

    ) : ViewModel() {

    fun removeExpenseById(expenseId: ExpenseId) {
        viewModelScope.launch {
            removeExpenseUseCase.invoke(expenseId)
        }
    }
}