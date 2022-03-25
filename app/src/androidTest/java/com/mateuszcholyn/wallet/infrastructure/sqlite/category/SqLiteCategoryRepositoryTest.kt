package com.mateuszcholyn.wallet.infrastructure.sqlite.category

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import com.mateuszcholyn.wallet.infrastructure.DatabaseTestSpecification
import com.mateuszcholyn.wallet.infrastructure.category.toEntityUpdate
import com.mateuszcholyn.wallet.randomNewCategory
import com.mateuszcholyn.wallet.randomNewExpense
import com.mateuszcholyn.wallet.randomString
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SqLiteCategoryRepositoryTest : DatabaseTestSpecification() {

    @Test
    fun shouldAddCategory() {
        //given
        val category = randomNewCategory()

        //when
        val addedCategory = categoryRepository.add(category)

        //then
        addedCategory equalsToIgnoringId category
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
    fun shouldNotUpdateCategoryIfIdIsNotPresent() {
        //given
        val category = categoryRepository.add(randomNewCategory())

        //when
        val updatedCategoriesSize = categoryDao.update(category.copy(id = 666L).toEntityUpdate())

        //then
        assertEquals(0, updatedCategoriesSize)
    }

    @Test
    fun shouldDeleteCategory() {
        //given
        val category = categoryRepository.add(randomNewCategory())

        //when
        val isRemoved = categoryRepository.remove(category.id)

        //then
        assertEquals(true, isRemoved)
    }

    @Test
    fun shouldReturnAllCategoryDetails() {
        //given
        val categoryWithOneExpense = categoryRepository.add(randomNewCategory())
        val categoryWithZeroExpense = categoryRepository.add(randomNewCategory())

        expenseRepository.add(randomNewExpense(categoryWithOneExpense))

        //when
        val categoryDetails = categoryRepository.getAllCategoriesWithExpenses()

        //then
        assertEquals(1, categoryDetails.find { it.category.id == categoryWithOneExpense.id }!!.expenses.size)
        assertEquals(0, categoryDetails.find { it.category.id == categoryWithZeroExpense.id }!!.expenses.size)
    }

}


infix fun ExistingCategory.equalsToIgnoringId(that: Category) {
    assertEquals(this.name, that.name)
}