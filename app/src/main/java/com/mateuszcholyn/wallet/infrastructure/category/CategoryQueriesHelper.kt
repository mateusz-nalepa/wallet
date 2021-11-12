package com.mateuszcholyn.wallet.infrastructure.category

import androidx.sqlite.db.SimpleSQLiteQuery

class CategoryQueriesHelper {

    fun getAllOrderByUsageDesc(): SimpleSQLiteQuery {
        val query = """
                select  Category.category_id, Category.name
                from Category
                LEFT JOIN Expense ON Category.category_id = Expense.fk_category_id
                GROUP BY Category.name
                ORDER BY count(Category.name) desc
                """.trimIndent()

        return SimpleSQLiteQuery(query)
    }

}