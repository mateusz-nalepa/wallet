package com.mateuszcholyn.wallet.domain.category.db

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CategoryEntityDaoTest : DatabaseTestSpecification() {

    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        categoryDao = db.categoryDao()
    }


    @Test
    fun shouldAddCategory() {
        //given
        val category = CategoryEntity(name = "All")

        //when
        val savedCategory = categoryDao.add(category)

        //then
        assertEquals(1L, savedCategory)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun shouldThrowUniqueException() {
        //given
        val category = CategoryEntity(name = "All")
        categoryDao.add(category)

        //when
        categoryDao.add(category)
    }

//    @Test
//    fun shouldDeleteCategory() {
//        //given
//        val category = CategoryEntity(name = "All")
//        categoryDao.add(category)
//
//        //when
//        val removed = categoryDao.remove("All")
//
//        //then
//        assertEquals(1, removed)
//    }

}
