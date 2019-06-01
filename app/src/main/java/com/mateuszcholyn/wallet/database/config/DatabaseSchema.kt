package com.mateuszcholyn.wallet.database.config

import android.provider.BaseColumns

object DatabaseSchema {

    const val ACTIVE = "1"
    const val NON_ACTIVE = "0"

    object ExpenseEntry : BaseColumns {
        const val TABLE_NAME = "expense"
        const val COLUMN_NAME_AMOUNT_VALUE = "value"
        const val COLUMN_NAME_CATEGORY_ID = "fk_category"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ACTIVE = "active"
    }

    object CategoryEntry : BaseColumns {
        const val TABLE_NAME = "category"
        const val COLUMN_NAME_CATEGORY_NAME = "category_name"
        const val COLUMN_ACTIVE = "active"
    }


    private const val SQL_CREATE_CATEGORY_TABLE =
            " CREATE TABLE IF NOT EXISTS ${DatabaseSchema.CategoryEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseSchema.CategoryEntry.COLUMN_ACTIVE} INTEGER," +
                    "${DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME} TEXT UNIQUE); "

    private const val SQL_CREATE_EXPENSE_TABLE =
            " CREATE TABLE IF NOT EXISTS ${DatabaseSchema.ExpenseEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_AMOUNT_VALUE} NUMERIC," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_ACTIVE} INTEGER," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_DESCRIPTION} TEXT," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_CATEGORY_ID} INTEGER," +
                    "${DatabaseSchema.ExpenseEntry.COLUMN_NAME_DATE} DATETIME," +
                    " FOREIGN KEY(${DatabaseSchema.ExpenseEntry.COLUMN_NAME_CATEGORY_ID}) " +
                    " REFERENCES ${DatabaseSchema.CategoryEntry.TABLE_NAME}(${BaseColumns._ID})); "


    private const val SQL_INITIAL_CATEGORIES =
            """
                 INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.COLUMN_NAME_CATEGORY_NAME}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES ('Zakupy', 1) ;
                 INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.COLUMN_NAME_CATEGORY_NAME}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES ('Jedzenie na mieście', 1) ;
                 INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.COLUMN_NAME_CATEGORY_NAME}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES ('Samochód', 1) ;
                 INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.COLUMN_NAME_CATEGORY_NAME}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES ('Leki', 1) ;
            """
    const val SQL_CREATE_DATABASE = SQL_CREATE_CATEGORY_TABLE + SQL_CREATE_EXPENSE_TABLE + SQL_INITIAL_CATEGORIES

    const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${DatabaseSchema.CategoryEntry.TABLE_NAME} " +
                    " DROP TABLE IF EXISTS ${DatabaseSchema.ExpenseEntry.TABLE_NAME}"


}
