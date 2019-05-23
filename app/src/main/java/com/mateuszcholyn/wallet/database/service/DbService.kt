package com.mateuszcholyn.wallet.database.service

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.mateuszcholyn.wallet.database.config.DatabaseSchema
import com.mateuszcholyn.wallet.database.model.ExpenseDto

class DbService(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getDb(context: Context) {

    }

    fun saveNewCategory(categoryName: String) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME, categoryName)
        }

        val newRowId = db?.insert(DatabaseSchema.CategoryEntry.TABLE_NAME, null, values)
    }

    fun getCategoryId(categoryName: String): Int {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID)

        val selection = "${DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME} = ?"
        val selectionArgs = arrayOf(categoryName)

        val cursor = db.query(
                DatabaseSchema.CategoryEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )

        cursor.moveToNext()

        val categoryId = cursor.getInt(0)

//        db.close()

        return categoryId
    }

    fun addExpense(expenseDto: ExpenseDto): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseSchema.ExpenseEntry.COLUMN_NAME_AMOUNT_VALUE, expenseDto.amount)
            put(DatabaseSchema.ExpenseEntry.COLUMN_NAME_CATEGORY_ID, getCategoryId(expenseDto.category))
            put(DatabaseSchema.ExpenseEntry.COLUMN_NAME_DATE, expenseDto.date.timeInMillis)
        }

// Insert the new row, returning the primary key value of the new row
        val insert = db.insert(DatabaseSchema.ExpenseEntry.TABLE_NAME, null, values)
        return insert
    }

}