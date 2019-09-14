package com.mateuszcholyn.wallet.domain.expense.service

import com.mateuszcholyn.wallet.domain.expense.db.ExpenseDao
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseQueriesHelper
import com.mateuszcholyn.wallet.domain.expense.mapper.ExpenseMapper
import com.mateuszcholyn.wallet.domain.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.asPrinteableAmount

class ExpenseService(private val expenseDao: ExpenseDao) {

    private val expenseMapper = ExpenseMapper()
    private val expenseQueriesHelper = ExpenseQueriesHelper()

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto {
        return expenseDao.add(expenseMapper.toEntity(expenseDto))
                .let {
                    expenseDto.id = it
                    expenseDto
                }
    }

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<ExpenseDto> {
        return expenseDao
                .getAll(expenseQueriesHelper.prepareExpenseSearchQuery(expenseSearchCriteria))
                .map { expenseMapper.fromEntity(it) }
    }

    fun averageExpense(averageSearchCriteria: AverageSearchCriteria): Double {
        return expenseDao
                .averageAmount(expenseQueriesHelper.prepareAverageSearchQuery(averageSearchCriteria))
                .asPrinteableAmount()
    }

    fun hardRemove(expenseId: Long): Boolean =
            expenseDao.remove(expenseId) == 1

    fun updateExpense(expenseDto: ExpenseDto): ExpenseDto {
        expenseDao.update(expenseMapper.toEntity(expenseDto))
        return expenseMapper.fromEntity(expenseDao.getExpenseWithCategory(expenseDto.id))
    }

}