package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.dateutils.toMillis

class ExpenseQueriesHelper {

    fun prepareExpenseSearchQuery(
            expenseSearchCriteria: ExpenseSearchCriteria,
    ): SimpleSQLiteQuery {
        var averageQuery = """
                select Expense.*, Category.* 
                from Expense
                LEFT JOIN Category ON Category.category_id = Expense.fk_category_id
                where Expense.date >= ${expenseSearchCriteria.beginDate.toMillis()}
                and Expense.date <= ${expenseSearchCriteria.endDate.toMillis()}
                and Expense.amount >= ${expenseSearchCriteria.fromAmount}
                and Expense.amount <= ${expenseSearchCriteria.toAmount}
                """.trimIndent()

        if (!expenseSearchCriteria.allCategories) {
            averageQuery += " and Category.category_id = '${expenseSearchCriteria.categoryId!!}' \n "
        }

        averageQuery += " ORDER BY ${expenseSearchCriteria.resolveSort()}"

        return SimpleSQLiteQuery(averageQuery)
    }

    private fun ExpenseSearchCriteria.resolveSort(): String =
            "Expense.${sort.field.name.lowercase()} ${sort.type.name.lowercase()}"

}