package com.mateuszcholyn.wallet.database

import android.database.sqlite.SQLiteDatabase


fun activeToModel(active: Int) =
        active == 1


fun checkIfStringExists(readableDb: SQLiteDatabase,
                        TableName: String,
                        dbfield: String,
                        fieldValue: String): Boolean {
    val query = "Select * from $TableName where $dbfield = '$fieldValue'"
    val cursor = readableDb.rawQuery(query, null)
    if (cursor.count <= 0) {
        cursor.close()
        return false
    }
    cursor.close()
    return true
}

