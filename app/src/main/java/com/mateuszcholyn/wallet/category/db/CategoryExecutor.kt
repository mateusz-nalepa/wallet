package com.mateuszcholyn.wallet.category.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.mateuszcholyn.wallet.category.model.CategoryDto
import com.mateuszcholyn.wallet.database.config.DatabaseHelper
import com.mateuszcholyn.wallet.database.config.DatabaseSchema

class CategoryExecutor(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private val writableDb = dbHelper.writableDatabase
    private val readableDb = dbHelper.readableDatabase

    fun saveNewCategory(categoryDto: CategoryDto): CategoryDto {
        val values = ContentValues().apply {
            put(DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME, categoryDto.name)
        }

        val categoryId = writableDb.insert(DatabaseSchema.CategoryEntry.TABLE_NAME, null, values)
        categoryDto.id = categoryId

        return categoryDto
    }

    fun getCategoryId(categoryName: String): Long {
        val projection = arrayOf(BaseColumns._ID)

        val selection = "${DatabaseSchema.CategoryEntry.COLUMN_NAME_CATEGORY_NAME} = ?"
        val selectionArgs = arrayOf(categoryName)

        val cursor = readableDb.query(
                DatabaseSchema.CategoryEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )

        cursor.moveToNext()
        val categoryId = cursor.getLong(0)

        cursor.close()
        return categoryId
    }

}