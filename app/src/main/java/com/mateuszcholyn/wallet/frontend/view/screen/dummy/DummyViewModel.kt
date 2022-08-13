package com.mateuszcholyn.wallet.frontend.view.screen.dummy

import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val allBackendServices: AllBackendServices,
) : ViewModel() {

    fun allBackendServices() = allBackendServices

    fun getAllCategories(): List<CategoryV2> =
        allBackendServices.categoryCoreServiceAPI.getAll()

    fun getAllExpenses(): List<ExpenseV2> =
        allBackendServices.expenseCoreServiceAPI.getAll()
}