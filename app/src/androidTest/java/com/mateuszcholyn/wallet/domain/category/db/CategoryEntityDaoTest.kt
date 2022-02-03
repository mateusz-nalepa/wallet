package com.mateuszcholyn.wallet.domain.category.db

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.randomNewCategory
import com.mateuszcholyn.wallet.randomString
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CategoryEntityDaoTest : DatabaseTestSpecification() {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        categoryDao = db.categoryDao()
        categoryRepository = SqLiteCategoryRepository(categoryDao)
    }

    @Test
    fun shouldAddCategory() {
        //given
        val category = randomNewCategory()

        //when
        val addedCategory = categoryRepository.add(category)

        //then
        addedCategory equalsToIgnoringId category
        assert(addedCategory.id != null)
    }

    @Test
    fun shouldAddMultipleCategories() {
        //given
        val numberOfCategories = 5

        //when
        repeat(numberOfCategories) {
            categoryRepository.add(randomNewCategory())
        }

        //then
        assertEquals(numberOfCategories, categoryDao.count())
    }

    @Test(expected = SQLiteConstraintException::class)
    fun shouldThrowUniqueException() {
        // given
        val category = randomNewCategory()
        categoryRepository.add(category)

        // when
        categoryRepository.add(category)
    }

    @Test
    fun shouldUpdateCategory() {
        //given
        val category = categoryRepository.add(randomNewCategory())
        val newCategoryName = randomString()

        //when
        val updatedCategory = categoryRepository.update(category.copy(name = newCategoryName))

        //then
        assertEquals(newCategoryName, updatedCategory.name)
    }

    @Test
    fun shouldDeleteCategory() {
        //given
        val category = categoryRepository.add(randomNewCategory())

        //when
        val isRemoved = categoryRepository.remove(category.id!!)

        //then
        assertEquals(true, isRemoved)
    }

}


infix fun Category.equalsToIgnoringId(that: Category) {
    assertEquals(this.name, that.name)
}