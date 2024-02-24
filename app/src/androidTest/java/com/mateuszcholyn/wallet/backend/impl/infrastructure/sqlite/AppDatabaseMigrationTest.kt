package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mateuszcholyn.wallet.backend.impl.di.repositories.addDatabaseMigrations
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CATEGORIES_TABLE_NAME
import com.mateuszcholyn.wallet.manager.randomCategoryName
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppDatabaseMigrationTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val testHelper: MigrationTestHelper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            AppDatabase::class.java,
        )

    @Test
    fun migrateAll() {
        // Create earliest version of the database.
        testHelper
            .createDatabase(TEST_DB, 1)
            .apply { close() }

        // Open latest version of the database. Room validates the schema
        // once all migrations execute.
        Room
            .databaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                AppDatabase::class.java,
                TEST_DB
            )
            .addDatabaseMigrations()
            .build()
            .apply {
                openHelper.writableDatabase.close()
            }
    }

    @Test
    fun testMigration1to2() {
        // given
        val database = testHelper.createDatabase(TEST_DB, 1)
        val indexesFromExpensesTableBeforeMigration = getAllIndexesFromExpensesTable(database)
        indexesFromExpensesTableBeforeMigration shouldNotContain "index_expenses_fk_category_id"

        // when
        testHelper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        // then
        val indexesFromExpensesTableAfterMigration = getAllIndexesFromExpensesTable(database)
        indexesFromExpensesTableAfterMigration shouldContain "index_expenses_fk_category_id"
    }

    @Test
    fun testMigration2to3() {
        // given
        val givenCategoryName = randomCategoryName()
        val database = testHelper.createDatabase(TEST_DB, 2)

        database.execSQL("INSERT INTO $CATEGORIES_TABLE_NAME VALUES('id1', '${givenCategoryName}')")

        // and adding category with same name throws exception
        shouldThrow<SQLiteConstraintException> {
            database.execSQL("INSERT INTO $CATEGORIES_TABLE_NAME VALUES('id2', '${givenCategoryName}')")
        }

        // when
        testHelper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_3)

        // then adding category with same name does not throws exception
        shouldNotThrowAny {
            database.execSQL("INSERT INTO $CATEGORIES_TABLE_NAME VALUES('id2', '${givenCategoryName}')")
        }
    }
}

fun getAllIndexesFromExpensesTable(database: SupportSQLiteDatabase): ArrayList<String> {
    val records = ArrayList<String>()
    val cursor = database.query("PRAGMA index_list('expenses');")
    if (cursor.moveToFirst()) {
        do {
            records.add(cursor.getString(1))
        } while (cursor.moveToNext())
    }
    cursor.close()
    return records
}
