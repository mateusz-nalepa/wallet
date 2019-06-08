package com.mateuszcholyn.wallet.expense.db

import android.content.ContentValues
import android.content.Context
import com.mateuszcholyn.wallet.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.database.ACTIVE
import com.mateuszcholyn.wallet.database.DatabaseHelper
import com.mateuszcholyn.wallet.database.ExpenseEntry
import com.mateuszcholyn.wallet.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.fromDbDate
import com.mateuszcholyn.wallet.util.toDbDate

class ExpenseExecutor(context: Context, private val categoryExecutor: CategoryExecutor) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private val writableDb = dbHelper.writableDatabase
    private val readableDb = dbHelper.readableDatabase

    fun addExpense(expenseDto: ExpenseDto): ExpenseDto {

        val values = ContentValues().apply {
            put(ExpenseEntry.COLUMN_AMOUNT, expenseDto.amount)
            put(ExpenseEntry.COLUMN_CATEGORY_ID, categoryExecutor.getCategoryId(expenseDto.category))
            put(ExpenseEntry.COLUMN_DATE, toDbDate(expenseDto.date))
            put(ExpenseEntry.COLUMN_DESCRIPTION, expenseDto.description)
            put(ExpenseEntry.COLUMN_ACTIVE, ACTIVE)
        }

        val insert = writableDb.insert(ExpenseEntry.TABLE_NAME, null, values)
        expenseDto.id = insert
        return expenseDto
    }

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<ExpenseDto> {
        val list = mutableListOf<ExpenseDto>()
        val cursor = readableDb.rawQuery(prepareSearchQuery(expenseSearchCriteria), null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(0)
                val description = cursor.getString(1)
                val amount = cursor.getDouble(2)
                val date = fromDbDate(cursor.getLong(3))
                val category = cursor.getString(4)

                list.add(ExpenseDto(
                        id = id,
                        amount = amount,
                        category = category,
                        date = date,
                        description = description))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }

    fun averageExpense(averageSearchCriteria: AverageSearchCriteria): Double {
        val cursor = readableDb.rawQuery(prepareAverageSearchQuery(averageSearchCriteria), null)

        cursor.moveToFirst()
        val amount = cursor.getDouble(0)
        cursor.close()

        return amount
    }

    fun hardRemove(expenseId: Long) =
            readableDb.delete(ExpenseEntry.TABLE_NAME, "${ExpenseEntry.ID} = $expenseId", null) > 0

    fun updateExpense(expenseDto: ExpenseDto): ExpenseDto {
        val cv = ContentValues()
        cv.put(ExpenseEntry.ID, expenseDto.id)
        cv.put(ExpenseEntry.COLUMN_AMOUNT, expenseDto.amount)
        cv.put(ExpenseEntry.COLUMN_CATEGORY_ID, categoryExecutor.getCategoryId(expenseDto.category))
        cv.put(ExpenseEntry.COLUMN_DATE, toDbDate(expenseDto.date))
        cv.put(ExpenseEntry.COLUMN_DESCRIPTION, expenseDto.description)

        writableDb.update(ExpenseEntry.TABLE_NAME, cv, "${ExpenseEntry.ID} = ${expenseDto.id}", null);
        return expenseDto
    }

}