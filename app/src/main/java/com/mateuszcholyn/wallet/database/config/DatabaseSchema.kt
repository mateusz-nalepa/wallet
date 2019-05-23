package com.mateuszcholyn.wallet.database.config

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
            " CREATE TABLE IF NOT EXISTS ${DatabaseSchema.CategoryEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME} TEXT UNIQUE); "

    private const val SQL_CREATE_EXPENSE_TABLE =
            " CREATE TABLE IF NOT EXISTS ${DatabaseSchema.ExpenseEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_AMOUNT_VALUE} NUMERIC," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_CATEGORY_ID} INTEGER," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_DATE} DATETIME," +
                    " FOREIGN KEY(${DatabaseSchema.ExpenseEntry.COLUMN_NAME_CATEGORY_ID}) " +
                    " REFERENCES ${DatabaseSchema.CategoryEntry.TABLE_NAME}(${BaseColumns._ID})); "

    const val SQL_CREATE_DATABASE = SQL_CREATE_CATEGORY_TABLE + SQL_CREATE_EXPENSE_TABLE


    const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${DatabaseSchema.CategoryEntry.TABLE_NAME} " +
                    " DROP TABLE IF EXISTS ${DatabaseSchema.ExpenseEntry.TABLE_NAME}"


}
