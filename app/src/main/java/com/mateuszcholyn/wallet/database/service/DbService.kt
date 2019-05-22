package com.mateuszcholyn.wallet.database.service

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.mateuszcholyn.wallet.database.model.DatabaseHelper
import com.mateuszcholyn.wallet.database.model.DatabaseSchema

class DbService(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getDb(context: Context) {

    }

    fun saveNewCategory(categoryName: String) {
        val db = dbHelper.writableDatabase

// Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME, categoryName)
        }

// Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(DatabaseSchema.CategoryEntry.TABLE_NAME, null, values)
    }

    fun getCategoryId(categoryName: String): Int {
        val db = dbHelper.readableDatabase

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID)

// Filter results WHERE "title" = 'My Title'
        val selection = "${DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME} = ?"
        val selectionArgs = arrayOf(categoryName)

// How you want the results sorted in the resulting Cursor
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

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

        db.close()

        return categoryId
    }

}