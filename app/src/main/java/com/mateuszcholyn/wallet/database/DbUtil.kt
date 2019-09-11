package com.mateuszcholyn.wallet.database

import android.database.sqlite.SQLiteDatabase


fun activeToModel(active: Int): Boolean =
        active == 1


fun checkIfStringExists(readableDb: SQLiteDatabase,
                        TableName: String,
                        dbField: String,
                        fieldValue: String): Boolean {
    val query = "Select * from $TableName where $dbField = '$fieldValue'"
    val cursor = readableDb.rawQuery(query, null)

    return cursor.use {
        cursor.count > 0
    }
}

