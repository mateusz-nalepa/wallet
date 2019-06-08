package com.mateuszcholyn.wallet.expense.service

import com.mateuszcholyn.wallet.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.util.asAmount

class ExpenseService(private val expenseExecutor: ExpenseExecutor) {

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto =
            expenseExecutor.addExpense(expenseDto)


    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria) =
        expenseExecutor.getAll(expenseSearchCriteria)

    fun averageExpense(averageSearchCriteria: AverageSearchCriteria) =
            expenseExecutor.averageExpense(averageSearchCriteria).asAmount()

}