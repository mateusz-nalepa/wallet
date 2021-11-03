package com.mateuszcholyn.wallet.database

import androidx.room.Room
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.database.AppDatabase
import org.junit.After
import org.junit.Before
import java.io.IOException

internal open class DatabaseTestSpecification {

    lateinit var db: AppDatabase

    @Before
    fun before() {
        val context = ApplicationContext.appContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

}