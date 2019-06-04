package com.mateuszcholyn.wallet.expense.service

import com.mateuszcholyn.wallet.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.expense.model.ExpenseCriteria
import com.mateuszcholyn.wallet.expense.model.ExpenseDto

class ExpenseService(private val expenseExecutor: ExpenseExecutor) {

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto =
            expenseExecutor.addExpense(expenseDto)


    fun getAll(expenseCriteria: ExpenseCriteria) =
        expenseExecutor.getAll(expenseCriteria)


}