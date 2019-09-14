package com.mateuszcholyn.wallet.domain.expense.mapper

import com.mateuszcholyn.wallet.domain.category.mapper.CategoryMapper
import com.mateuszcholyn.wallet.domain.expense.db.model.Expense
import com.mateuszcholyn.wallet.domain.expense.db.model.ExpenseWithCategory
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto

class ExpenseMapper {

    private val categoryMapper = CategoryMapper()

    fun toEntity(expenseDto: ExpenseDto): Expense {
        val id = expenseDto.id
        return Expense(
                expenseId = if (id != -1L) id else null,
                amount = expenseDto.amount,
                description = expenseDto.description,
                date = expenseDto.date,
                fkCategoryId = expenseDto.category.id
        )
    }

    fun fromEntity(expenseWithCategory: ExpenseWithCategory): ExpenseDto {
        val expense = expenseWithCategory.expense
        val category = expenseWithCategory.category
        return ExpenseDto(
                id = expense.expenseId!!,
                amount = expense.amount!!,
                description = expense.description!!,
                date = expense.date!!,
                category = categoryMapper.fromEntity(category)
        )
    }

}
