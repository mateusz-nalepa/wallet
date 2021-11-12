package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.toMillis

class ExpenseQueriesHelper {

    fun prepareAverageSearchQuery(expenseSearchCriteria: ExpenseSearchCriteria): SimpleSQLiteQuery {
        return prepareExpenseSearchQueryXDDD(
            selectClausal = "select avg (Expense.amount) ",
            expenseSearchCriteria = expenseSearchCriteria,
        )
    }

    fun prepareExpenseSearchQuery(expenseSearchCriteria: ExpenseSearchCriteria): SimpleSQLiteQuery {
        return prepareExpenseSearchQueryXDDD(
            selectClausal = "select Expense.*, Category.* ",
            expenseSearchCriteria = expenseSearchCriteria,
        )
    }

    private fun prepareExpenseSearchQueryXDDD(
        selectClausal: String,
        expenseSearchCriteria: ExpenseSearchCriteria
    ): SimpleSQLiteQuery {
        var averageQuery = """
                $selectClausal
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