package com.mateuszcholyn.wallet.category.db

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.mateuszcholyn.wallet.category.model.CategoryDto
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.database.*

class CategoryExecutor(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private val writableDb = dbHelper.writableDatabase
    private val readableDb = dbHelper.readableDatabase

    fun saveNewCategory(categoryDto: CategoryDto): CategoryDto {
        if (checkIfStringExists(readableDb, CategoryEntry.TABLE_NAME, CategoryEntry.COLUMN_CATEGORY, categoryDto.name)) {
            Toast
                    .makeText(ApplicationContext.appContext, "Kategoria '${categoryDto.name}' już istnieje w bazie!", Toast.LENGTH_SHORT)
                    .show()
            return categoryDto.copy(
                    id = getCategoryId(categoryDto.name)
            )
        }
        val values = ContentValues().apply {
            put(CategoryEntry.COLUMN_CATEGORY, categoryDto.name)
            put(CategoryEntry.COLUMN_ACTIVE, ACTIVE)

        }

        val categoryId = writableDb.insertOrThrow(CategoryEntry.TABLE_NAME, null, values)
        categoryDto.id = categoryId

        return categoryDto
    }

    fun getCategoryId(categoryName: String): Long {
        val projection = arrayOf(CategoryEntry.ID)

        val selection = "${CategoryEntry.COLUMN_CATEGORY} = ?"
        val selectionArgs = arrayOf(categoryName)

        val cursor = readableDb.query(
                CategoryEntry.TABLE_NAME,   // The table to query
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

    fun getAll(): List<CategoryDto> {
        val list = mutableListOf<CategoryDto>()
        val cursor = readableDb.rawQuery("select * from ${CategoryEntry.TABLE_NAME} where ${CategoryEntry.COLUMN_ACTIVE} = 1", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(cursor.getColumnIndex(CategoryEntry.ID))
                val categoryName = cursor.getString(cursor.getColumnIndex(CategoryEntry.COLUMN_CATEGORY))
                val active = activeToModel(cursor.getInt(cursor.getColumnIndex(CategoryEntry.COLUMN_ACTIVE)))

                list.add(CategoryDto(id, active, categoryName))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }

}