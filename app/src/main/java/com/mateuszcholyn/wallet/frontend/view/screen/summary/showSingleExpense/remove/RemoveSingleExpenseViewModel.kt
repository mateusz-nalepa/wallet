package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveSingleExpenseViewModel @Inject constructor(
    private val removeExpenseUseCase: RemoveExpenseUseCase,
) : ViewModel() {



}
