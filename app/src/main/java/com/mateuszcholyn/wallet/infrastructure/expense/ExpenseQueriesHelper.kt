package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.domain.expense.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.toMillis

class ExpenseQueriesHelper {

    fun prepareAverageSearchQuery(averageSearchCriteria: AverageSearchCriteria): SimpleSQLiteQuery {
        var averageQuery = """
                select avg (Expense.amount)
                from Expense
                LEFT JOIN Category ON Category.category_id = Expense.fk_category_id
                where Expense.date >= ${averageSearchCriteria.beginDate.toMillis()}
                and Expense.date <= ${averageSearchCriteria.endDate.toMillis()}
                """.trimIndent()

        return if (averageSearchCriteria.allCategories) {
            SimpleSQLiteQuery(averageQuery)
        } else {
            averageQuery += " and Category.name = '${averageSearchCriteria.categoryName!!}'"
            SimpleSQLiteQuery(averageQuery)
        }
    }

    fun prepareExpenseSearchQuery(expenseSearchCriteria: ExpenseSearchCriteria): SimpleSQLiteQuery {
        var averageQuery = """
                select Expense.*, Category.*
                from Expense
                LEFT JOIN Category ON Category.category_id = Expense.fk_category_id
                where Expense.date >= ${expenseSearchCriteria.beginDate.toMillis()}
                and Expense.date <= ${expenseSearchCriteria.endDate.toMillis()}
                """.trimIndent()

        return if (expenseSearchCriteria.allCategories) {
            SimpleSQLiteQuery(averageQuery)
        } else {
            averageQuery += " and Category.name = '${expenseSearchCriteria.categoryName!!}'"
            SimpleSQLiteQuery(averageQuery)
        }
    }


}