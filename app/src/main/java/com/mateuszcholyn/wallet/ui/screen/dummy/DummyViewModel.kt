package com.mateuszcholyn.wallet.ui.screen.dummy

import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.newcode.app.backend.BackendIsPropertySetup
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val categoryService: CategoryService,
    private val expenseService: ExpenseService,
    // Temporary added to verify hilt use cases setup
    private val backendIsPropertySetup: BackendIsPropertySetup,
) : ViewModel() {

    init {
        println("DummyViewModel: BackendIsPropertySetup")
    }

    fun expenseService(): ExpenseService = expenseService
    fun categoryService(): CategoryService = categoryService
}