package com.mateuszcholyn.wallet.util

import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.domain.DemoModeEnabled
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
import java.math.BigDecimal
import java.time.LocalDateTime


class SimpleDiScope {
    val categoryRepository: CategoryRepository = SimpleCategoryRepository()
    val expenseRepository: ExpenseRepository = SimpleExpenseRepository()
}


fun simpleDi(initScope: SimpleDiScope.() -> Unit): DI {

    val diScope = SimpleDiScope().apply(initScope)

    val firstCategory = diScope.categoryRepository.add(Category(name = "Test Category 1"))

    diScope.expenseRepository.add(Expense(amount = BigDecimal("15.0"), date = LocalDateTime.now().minusHours(1), category = firstCategory, description = "expense 1"))

    val secondCategory = diScope.categoryRepository.add(Category(name = "Test Category 2"))
    diScope.expenseRepository.add(Expense(amount = BigDecimal("20.0"), date = LocalDateTime.now().minusHours(2), category = secondCategory, description = "expense 2"))
    diScope.expenseRepository.add(Expense(amount = BigDecimal("33.0"), date = LocalDateTime.now().minusHours(3), category = secondCategory, description = "expense 3"))


    val testDI by DI.lazy {
        //Demo Mode
        bind<DemoAppEnabledProvider>() with provider { DemoModeEnabled }

        //Category
        bind<CategoryRepository>() with provider { diScope.categoryRepository }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseRepository>() with provider { diScope.expenseRepository }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }
    }
    return testDI
}


class SimpleCategoryRepository : CategoryRepository {
    private val storage = mutableMapOf<Long, Category>()
    private val idGenerator = IdGenerator()

    override fun getAllOrderByUsageDesc(): List<Category> =
            getAllWithDetailsOrderByUsageDesc()
                    .map { it.toCategory() }


    override fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> =
            storage.values
                    .toList()
                    .groupBy { it.name }
                    .mapValues {
                        CategoryIdWithNumberOfExpenses(
                                categoryId = it.value.first().id
                                        ?: throw IllegalStateException("Id should not be null"),
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

    override fun update(category: Category): Category {
        storage[category.id!!] = category

        return category
    }
}

class SimpleExpenseRepository : ExpenseRepository {
    private val storage = mutableMapOf<Long, Expense>()
    private val idGenerator = IdGenerator()

    override fun remove(expenseId: Long): Boolean {
        storage.remove(expenseId)
        return true
    }

    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return storage.values.toList()
    }

    override fun add(expense: Expense): Expense {
        val expenseId = idGenerator.nextNumber()
        val addedExpense = expense.copy(id = expenseId)

        storage[expenseId] = addedExpense

        return addedExpense
    }

    override fun update(expense: Expense): Expense {
        storage[expense.id!!] = expense

        return expense
    }

    override fun getById(expenseId: Long): Expense {
        return storage[expenseId]!!
    }

}

class IdGenerator {
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