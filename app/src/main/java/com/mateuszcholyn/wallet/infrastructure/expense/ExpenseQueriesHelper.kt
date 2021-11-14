package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.toMillis

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
                """.trimIndent()

        if (!expenseSearchCriteria.allCategories) {
            averageQuery += " and Category.name = '${expenseSearchCriteria.categoryName!!}' \n "
        }

        averageQuery += " ORDER BY Expense.date desc"

        return SimpleSQLiteQuery(averageQuery)
    }

}