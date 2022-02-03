package com.mateuszcholyn.wallet.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mateuszcholyn.wallet.config.AppDatabase
import org.junit.After
import org.junit.Before
import java.io.IOException

internal open class DatabaseTestSpecification {

    lateinit var db: AppDatabase

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

}