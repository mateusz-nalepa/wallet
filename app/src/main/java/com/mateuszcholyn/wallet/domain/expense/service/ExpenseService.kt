package com.mateuszcholyn.wallet.domain.expense.service

import com.mateuszcholyn.wallet.domain.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.domain.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.asAmount

class ExpenseService(private val expenseExecutor: ExpenseExecutor) {

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto =
            expenseExecutor.addExpense(expenseDto)

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria) =
            expenseExecutor.getAll(expenseSearchCriteria)

    fun averageExpense(averageSearchCriteria: AverageSearchCriteria) =
            expenseExecutor.averageExpense(averageSearchCriteria).asAmount()

    fun hardRemove(expenseId: Long) =
            expenseExecutor.hardRemove(expenseId)

    fun updateExpense(expenseDto: ExpenseDto) =
            expenseExecutor.updateExpense(expenseDto)

}