package com.mateuszcholyn.wallet.di.demodi

import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.domain.DemoModeEnabled
import com.mateuszcholyn.wallet.domain.category.*
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import java.math.BigDecimal
import java.time.LocalDateTime


class SimpleDiScope {
    val expenseRepository = SimpleExpenseRepository()
    val categoryRepository: CategoryRepository = SimpleCategoryRepository(expenseRepository)
}


fun simpleDi(
        initScope: SimpleDiScope.() -> Unit,
): DI {

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


class SimpleCategoryRepository(
        private val expenseRepository: SimpleExpenseRepository,
) : CategoryRepository {
    private val storage = mutableMapOf<Long, ExistingCategory>()
    private val idGenerator = IdGenerator()

    override fun remove(categoryId: Long): Boolean {
        storage.remove(categoryId)
        return true
    }

    override fun add(category: Category): ExistingCategory {
        val categoryId = idGenerator.nextNumber()

        val addedCategory =
                ExistingCategory(
                        id = categoryId,
                        name = category.name,
                )

        storage[categoryId] = addedCategory

        return addedCategory
    }

    override fun update(category: ExistingCategory): ExistingCategory {
        storage[category.id] = category

        return category
    }

    override fun getAllCategoriesWithExpenses(): List<CategoryWithExpenses> {
        return storage.values
                .map { category ->
                    CategoryWithExpenses(
                            category = category,
                            expenses = expenseRepository.getAll().filter { expense ->
                                expense.category.id == category.id
                            }
                    )
                }
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

    fun getAll(): List<Expense> {
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