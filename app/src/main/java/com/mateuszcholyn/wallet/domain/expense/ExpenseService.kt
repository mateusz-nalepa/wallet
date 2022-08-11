package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchSingleResult
import java.math.BigDecimal
import java.time.Duration

data class SummaryResult(
    val expenses: List<Expense>,
    val averageExpenseResult: AverageExpenseResult,
)

class ExpenseService(
    private val expenseRepository: ExpenseRepository,
) {

//    fun getById(expenseId: Long): Expense {
//        return expenseRepository.getById(expenseId)
//    }
//
//    fun getSummary(expenseSearchCriteria: ExpenseSearchCriteria): SummaryResult {
//        val expenses = expenseRepository.getAll(expenseSearchCriteria)
//
//        return SummaryResult(
//            expenses = expenses,
//            averageExpenseResult = averageExpense(expenses, expenseSearchCriteria),
//        )
//    }
//
//    private fun averageExpense(
//        expenses: List<Expense>,
//        expenseSearchCriteria: ExpenseSearchCriteria,
//    ): AverageExpenseResult {
//        TODO("remove me actually!")
//    }
//
//    fun hardRemove(expenseId: Long): Boolean =
//        expenseRepository.remove(expenseId)
//
//    fun removeAll(): Boolean {
//        return expenseRepository.removeAll()
//    }
//
//    fun saveExpense(expense: Expense): Expense =
//        if (expense.id != null) {
//            updateExpense(expense)
//        } else {
//            addExpense(expense)
//        }
//
//    private fun updateExpense(expense: Expense): Expense {
//        return expenseRepository.update(expense)
//    }
//
//    private fun addExpense(expense: Expense): Expense {
//        return expenseRepository.add(expense)
//    }

}

data class AverageExpenseResult(
    val wholeAmount: BigDecimal,
    val days: Int,
    val averageAmount: BigDecimal,
)

//fun List<Expense>.sumExpensesAmount(): BigDecimal =
//    if (this.isNotEmpty()) {
//        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
//    } else {
//        BigDecimal.ZERO
//    }

fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
    if (this.isNotEmpty()) {
        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    } else {
        BigDecimal.ZERO
    }


private fun ExpenseSearchCriteria.toNumberOfDays(): Long =
    Duration.between(this.beginDate, this.endDate)
        .toDays()
