package com.mateuszcholyn.wallet.expense.service

import android.content.Context
import com.mateuszcholyn.wallet.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.expense.model.ExpenseDto

class ExpenseService(val context: Context) {

    private val expenseExecutor: ExpenseExecutor = ExpenseExecutor(context)

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto =
            expenseExecutor.addExpense(expenseDto)

}