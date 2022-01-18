package com.mateuszcholyn.wallet.util

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.infrastructure.category.CategoryIdWithNumberOfExpenses
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import java.time.LocalDateTime

fun previewDi(): DI {
    val previewDi by DI.lazy {
        bind<CategoryRepository>() with provider { PreviewCategoryRepository() }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseRepository>() with provider { PreviewExpenseRepository() }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }
    }
    return previewDi
}


class PreviewCategoryRepository : CategoryRepository {
    private val storage = mutableMapOf<Long, Category>()
    private val idGenerator = PreviewIdGenerator()

    override fun getAllOrderByUsageDesc(): List<Category> =
            getAllWithDetailsOrderByUsageDesc()
                    .map { it.toCategory() }


    override fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> =
            storage.values
                    .toList()
                    .groupBy { it.name }
                    .mapValues {
                        CategoryIdWithNumberOfExpenses(
                                categoryId = it.value.first().id,
                                numberOfExpenses = it.value.size.toLong(),
                        )
                    }
                    .toList()
                    .map {
                        CategoryDetails(
                                id = it.second.categoryId,
                                name = it.first,
                                numberOfExpenses = it.second.numberOfExpenses,
                        )
                    }
                    .sortedByDescending { it.numberOfExpenses }

    override fun remove(categoryId: Long): Boolean {
        storage.remove(categoryId)
        return true
    }

    override fun add(category: Category): Category {
        val categoryId = idGenerator.nextNumber()
        val addedCategory = category.copy(id = categoryId)

        storage[categoryId] = addedCategory

        return addedCategory
    }
}

class PreviewExpenseRepository : ExpenseRepository {
    private val storage = mutableMapOf<Long, Category>()
    private val idGenerator = PreviewIdGenerator()

    override fun remove(expenseId: Long): Boolean {
        return true
    }

    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return emptyList()
    }

    override fun add(expense: Expense): Expense {
        return previewRandomExpense()
    }

    override fun update(expense: Expense): Expense {
        return previewRandomExpense()
    }

    override fun getById(expenseId: Long): Expense {
        return previewRandomExpense()
    }

}

fun previewRandomExpense(): Expense =
        Expense(
                id = 1L,
                amount = 5.0,
                date = LocalDateTime.now(),
                description = "XD",
                category = randomCategory(),
        )

fun randomCategory(): Category {
    return Category(
            id = 1L,
            name = "Test",
    )
}

class PreviewIdGenerator {
    var init = 1L

    fun nextNumber(): Long {
        return init.also { init++ }
    }

}

private fun CategoryDetails.toCategory(): Category =
        Category(
                id = id,
                name = name,
        )