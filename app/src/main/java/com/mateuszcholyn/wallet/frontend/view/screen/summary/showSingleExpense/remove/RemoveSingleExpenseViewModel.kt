package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoveSingleExpenseViewModel @Inject constructor(
    private val removeExpenseUseCase: RemoveExpenseUseCase,
) : ViewModel() {


    val exposedUiState: MutableState<RemoveSingleExpenseUiState> = mutableStateOf(RemoveSingleExpenseUiState())
    private var uiState by MutableStateDelegate(exposedUiState)

    fun closeRemoveModalDialog() {
        uiState = uiState.copy(isRemovalDialogVisible = false)
    }

    fun closeErrorModalDialog() {
        uiState = uiState.copy(errorModalState = ErrorModalState.NotVisible)
    }

    fun showRemoveConfirmationDialog() {
        uiState = uiState.copy(isRemovalDialogVisible = true)
    }

    fun removeExpenseById(
        expenseId: ExpenseId,
        onExpenseRemovedAction: () -> Unit,
    ) {
        viewModelScope.launch { // DONE UI State - tak częściowo XD
            try {
                removeExpenseUseCase.invoke(expenseId)
                onExpenseRemovedAction.invoke()
                uiState = uiState.copy(isRemovalDialogVisible = false)
            } catch (t: Throwable) {
                uiState = uiState.copy(
                    isRemovalDialogVisible = false,
                    errorModalState = ErrorModalState.Visible("usuwanie się nie udało")
                )
            }
        }
    }
}
