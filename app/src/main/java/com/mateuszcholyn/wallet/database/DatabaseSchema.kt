package com.mateuszcholyn.wallet.database


object CategoryEntry {
    const val TABLE_NAME = "category"
    const val ID = "id"
    const val COLUMN_CATEGORY = "category_name"
    const val COLUMN_ACTIVE = "active"
}

object ExpenseEntry {
    const val TABLE_NAME = "expense"
    const val ID = "id"
    const val COLUMN_AMOUNT = "value"
    const val COLUMN_CATEGORY_ID = "fk_category"
    const val COLUMN_DATE = "date"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_ACTIVE = "active"
}

const val ACTIVE = "1"

object DatabaseSchema {

    const val SQL_CREATE_CATEGORY_TABLE =
            " CREATE TABLE IF NOT EXISTS ${CategoryEntry.TABLE_NAME} (" +
                    "${CategoryEntry.ID} INTEGER PRIMARY KEY," +
                    "${CategoryEntry.COLUMN_ACTIVE} INTEGER," +
                    "${CategoryEntry.COLUMN_CATEGORY} TEXT UNIQUE); "

    const val SQL_CREATE_EXPENSE_TABLE =
            " CREATE TABLE IF NOT EXISTS ${ExpenseEntry.TABLE_NAME} (" +
                    "${ExpenseEntry.ID} INTEGER PRIMARY KEY," +
                    "${ExpenseEntry.COLUMN_AMOUNT} NUMERIC," +
                    "${ExpenseEntry.COLUMN_ACTIVE} INTEGER," +
                    "${ExpenseEntry.COLUMN_DESCRIPTION} TEXT," +
                    "${ExpenseEntry.COLUMN_CATEGORY_ID} INTEGER," +
                    "${ExpenseEntry.COLUMN_DATE} DATETIME," +
                    " FOREIGN KEY(${ExpenseEntry.COLUMN_CATEGORY_ID}) " +
                    " REFERENCES ${CategoryEntry.TABLE_NAME}(${CategoryEntry.ID})); "


    const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${CategoryEntry.TABLE_NAME};  " +
                    " DROP TABLE IF EXISTS ${ExpenseEntry.TABLE_NAME}; "


}
