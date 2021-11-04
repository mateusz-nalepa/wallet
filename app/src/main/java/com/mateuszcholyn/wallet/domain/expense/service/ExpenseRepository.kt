package com.mateuszcholyn.wallet.domain.expense.service

import com.mateuszcholyn.wallet.domain.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.Expense
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseWithCategory
import org.joda.time.LocalDateTime

interface ExpenseRepository {

    fun getAll(): List<Expense>
    fun getExpenseWithCategory(expenseId: Long): ExpenseWithCategory
    fun remove(expenseId: Long): Boolean
    fun moneySpentBetween(start: LocalDateTime, end: LocalDateTime): Double
    fun averageAmount(averageSearchCriteria: AverageSearchCriteria): Double
    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense>
    fun add(expense: Expense): Expense
    fun update(expense: Expense): Expense

}
