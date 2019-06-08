package com.mateuszcholyn.wallet.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseSchema.SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(DatabaseSchema.SQL_CREATE_EXPENSE_TABLE)
        db.execSQL("INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.ID}, ${CategoryEntry.COLUMN_CATEGORY}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES (1, 'Mieszkanie', 1) ;")
        db.execSQL("INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.ID}, ${CategoryEntry.COLUMN_CATEGORY}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES (2, 'Zakupy', 1) ;")
        db.execSQL("INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.ID}, ${CategoryEntry.COLUMN_CATEGORY}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES (3, 'Jedzenie na mieście', 1) ;")
        db.execSQL("INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.ID}, ${CategoryEntry.COLUMN_CATEGORY}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES (4, 'Paliwo', 1) ;")
        db.execSQL("INSERT INTO ${CategoryEntry.TABLE_NAME} (${CategoryEntry.ID}, ${CategoryEntry.COLUMN_CATEGORY}, ${CategoryEntry.COLUMN_ACTIVE}) VALUES (5, 'Leki', 1) ;")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseSchema.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "expense7.db"
    }
}