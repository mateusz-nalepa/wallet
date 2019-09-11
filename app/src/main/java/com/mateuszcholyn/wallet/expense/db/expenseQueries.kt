package com.mateuszcholyn.wallet.expense.db

import com.mateuszcholyn.wallet.database.CategoryEntry
import com.mateuszcholyn.wallet.database.ExpenseEntry
import com.mateuszcholyn.wallet.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.expense.model.isAllCategories
import com.mateuszcholyn.wallet.util.toDbDate

fun prepareSearchQuery(expenseSearchCriteria: ExpenseSearchCriteria): String {

    return """
            select
                    ${ExpenseEntry.TABLE_NAME}.${ExpenseEntry.ID},
                    ${ExpenseEntry.COLUMN_DESCRIPTION},
                    ${ExpenseEntry.COLUMN_AMOUNT},
                    ${ExpenseEntry.COLUMN_DATE},
                    ${CategoryEntry.COLUMN_CATEGORY}
                from ${ExpenseEntry.TABLE_NAME}
                LEFT JOIN ${CategoryEntry.TABLE_NAME} ON
                ${CategoryEntry.TABLE_NAME}.${CategoryEntry.ID} = ${ExpenseEntry.TABLE_NAME}.${ExpenseEntry.COLUMN_CATEGORY_ID}
                where ${ExpenseEntry.TABLE_NAME}.${ExpenseEntry.COLUMN_ACTIVE} = 1
                ${if (expenseSearchCriteria.isAllCategories()) "" else " and ${CategoryEntry.COLUMN_CATEGORY} = '${expenseSearchCriteria.categoryName}' "}
                and ${ExpenseEntry.COLUMN_DATE} >= ${toDbDate(expenseSearchCriteria.beginDate)}
                and ${ExpenseEntry.COLUMN_DATE} <= ${toDbDate(expenseSearchCriteria.endDate)}
                ORDER BY ${ExpenseEntry.COLUMN_DATE} DESC
                """.trimIndent()
}

fun prepareAverageSearchQuery(averageSearchCriteria: AverageSearchCriteria): String {
    return """
            select avg (${ExpenseEntry.COLUMN_AMOUNT})
                from ${ExpenseEntry.TABLE_NAME}
                LEFT JOIN ${CategoryEntry.TABLE_NAME} ON
                ${CategoryEntry.TABLE_NAME}.${CategoryEntry.ID} = ${ExpenseEntry.TABLE_NAME}.${ExpenseEntry.COLUMN_CATEGORY_ID}
                where ${ExpenseEntry.TABLE_NAME}.${ExpenseEntry.COLUMN_ACTIVE} = 1
                ${if (averageSearchCriteria.isAllCategories()) "" else " and ${CategoryEntry.COLUMN_CATEGORY} = '${averageSearchCriteria.categoryName}' "}
                and ${ExpenseEntry.COLUMN_DATE} >= ${toDbDate(averageSearchCriteria.beginDate)}
                and ${ExpenseEntry.COLUMN_DATE} <= ${toDbDate(averageSearchCriteria.endDate)}
                ORDER BY ${ExpenseEntry.COLUMN_DATE} DESC
                """.trimIndent()
}