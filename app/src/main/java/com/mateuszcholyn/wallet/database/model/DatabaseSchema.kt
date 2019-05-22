package com.mateuszcholyn.wallet.database.model

import android.provider.BaseColumns

object DatabaseSchema {

    object ExpenseEntry : BaseColumns {
        const val TABLE_NAME = "expense"
        const val COLUMN_NAME_AMOUNT_VALUE = "value"
        const val COLUMN_NAME_CATEGORY_ID = "fk_category"
        const val COLUMN_NAME_DATE = "date"
    }

    object CategoryEntry : BaseColumns {
        const val TABLE_NAME = "category"
        const val COLUMN_NAME_CATEGORY_NAME = "category_name"
    }


    private const val SQL_CREATE_CATEGORY_TABLE =
            " CREATE TABLE IF NOT EXISTS ${CategoryEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${CategoryEntry.COLUMN_NAME_CATEGORY_NAME} TEXT UNIQUE); "

    private const val SQL_CREATE_EXPENSE_TABLE =
            " CREATE TABLE IF NOT EXISTS ${ExpenseEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${ExpenseEntry.COLUMN_NAME_AMOUNT_VALUE} NUMERIC," +
                    "${ExpenseEntry.COLUMN_NAME_CATEGORY_ID} INTEGER," +
                    "${ExpenseEntry.COLUMN_NAME_DATE} DATETIME," +
                    " FOREIGN KEY(${ExpenseEntry.COLUMN_NAME_CATEGORY_ID}) " +
                    " REFERENCES ${CategoryEntry.TABLE_NAME}(${BaseColumns._ID})); "

    const val SQL_CREATE_DATABASE = SQL_CREATE_CATEGORY_TABLE + SQL_CREATE_EXPENSE_TABLE


    const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${CategoryEntry.TABLE_NAME} " +
                    " DROP TABLE IF EXISTS ${ExpenseEntry.TABLE_NAME}"


}
