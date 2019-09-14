package com.mateuszcholyn.wallet.domain.category.db

import android.database.sqlite.SQLiteConstraintException
import android.support.test.runner.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.domain.category.db.model.Category
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CategoryDaoTest : DatabaseTestSpecification() {

    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        categoryDao = db.categoryDao()
    }


    @Test
    fun shouldAddCategory() {
        //given
        val category = Category(name = "All")

        //when
        val savedCategory = categoryDao.add(category)

        //then
        savedCategory == 1L
    }

    @Test(expected = SQLiteConstraintException::class)
    fun shouldThrowUniqueException() {
        //given
        val category = Category(name = "All")
        categoryDao.add(category)

        //when
        categoryDao.add(category)
    }

    @Test
    fun shouldFetchCategoryId() {
        //given
        val category = Category(name = "All")
        categoryDao.add(category)

        //when
        val categoryId = categoryDao.getCategoryIdByName("All")

        //then
        categoryId == 1L
    }

    @Test
    fun shouldDeleteCategory() {
        //given
        val category = Category(name = "All")
        categoryDao.add(category)

        //when
        val removed = categoryDao.remove("All")

        //then
        removed == 1
    }




}
