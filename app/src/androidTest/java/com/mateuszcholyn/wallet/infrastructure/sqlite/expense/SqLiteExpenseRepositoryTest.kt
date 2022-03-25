package com.mateuszcholyn.wallet.infrastructure.sqlite.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.infrastructure.DatabaseTestSpecification
import com.mateuszcholyn.wallet.randomDescription
import com.mateuszcholyn.wallet.randomNewCategory
import com.mateuszcholyn.wallet.randomNewExpense
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SqLiteExpenseRepositoryTest : DatabaseTestSpecification() {

    @Test
    fun shouldAddExpense() {
        //given
        val existingCategory = categoryRepository.add(randomNewCategory())
        val expense = randomNewExpense(existingCategory)

        //when
        val addedExpense = expenseRepository.add(expense)

        //then
        expenseRepository.getById(addedExpense.idOrThrow()) equalsToIgnoringId expense
        assert(addedExpense.id != null)
    }

    @Test
    fun shouldAddMultipleExpensesToOneCategory() {
        //given
        val existingCategory = categoryRepository.add(randomNewCategory())
        val numberOfExpenses = 5

        //when
        repeat(numberOfExpenses) {
            expenseRepository.add(randomNewExpense(existingCategory))
        }

        //then
        val expensesEntities = categoryDao.getAllCategoriesWithExpenses()
        assertEquals(numberOfExpenses, expensesEntities.size)
        expensesEntities.forEach { categoryWithExpense ->
            assertEquals(categoryWithExpense.expenseEntity!!.fkCategoryId, categoryWithExpense.categoryEntity.categoryId)
        }
    }

    @Test
    fun shouldUpdateExpense() {
        //given
        val existingCategory = categoryRepository.add(randomNewCategory())
        val addedExpense = expenseRepository.add(randomNewExpense(existingCategory))
        val newDescription = randomDescription()

        //when
        val updatedExpense = expenseRepository.update(addedExpense.copy(description = newDescription))

        //then
        assertEquals(newDescription, updatedExpense.description)
    }

    @Test
    fun shouldDeleteExpense() {
        //given
        val existingCategory = categoryRepository.add(randomNewCategory())
        val addedExpense = expenseRepository.add(randomNewExpense(existingCategory))

        //when
        val isRemoved = expenseRepository.remove(addedExpense.idOrThrow())

        //then
        assertEquals(true, isRemoved)
    }
}


infix fun Expense.equalsToIgnoringId(that: Expense) {
    assertEquals(this.amount, that.amount)
    assertEquals(this.date, that.date)
    assertEquals(this.description, that.description)
    assertEquals(this.category, that.category)
}