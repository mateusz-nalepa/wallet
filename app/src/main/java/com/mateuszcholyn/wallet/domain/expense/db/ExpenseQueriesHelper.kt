package com.mateuszcholyn.wallet.domain.expense.db

import android.arch.persistence.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.domain.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.isAllCategories
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

        return if (averageSearchCriteria.isAllCategories()) {
            SimpleSQLiteQuery(averageQuery)
        } else {
            averageQuery += " and Category.name = '${averageSearchCriteria.categoryName}'"
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

        return if (expenseSearchCriteria.isAllCategories()) {
            SimpleSQLiteQuery(averageQuery)
        } else {
            averageQuery += " and Category.name = '${expenseSearchCriteria.categoryName}'"
            SimpleSQLiteQuery(averageQuery)
        }
    }


}