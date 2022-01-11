package com.mateuszcholyn.wallet.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.infrastructure.category.CategoryIdWithNumberOfExpenses
import com.mateuszcholyn.wallet.scaffold.MainScreen
import org.junit.Rule
import org.junit.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import org.kodein.di.provider
import java.time.LocalDateTime

class CategoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun myFirstTest() {
        // Start the app
        val categoryRepository = TestCategoryRepository()
        composeTestRule.setContent {
            withDI(di = createDi(categoryRepository)) {
                MaterialTheme {
                    ProvideWindowInsets {
                        val systemUiController = rememberSystemUiController()
                        val darkIcons = MaterialTheme.colors.isLight
                        SideEffect {
                            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
                        }
                        MainScreen()
                    }
                }

            }

        }

        composeTestRule.goToCategoryScreen()

        composeTestRule.onNodeWithTag("NewCategoryTextField").performTextInput("XDDD")
        composeTestRule.onNodeWithTag("AddNewCategoryButton").performClick()

        assert(categoryRepository.getAllOrderByUsageDesc().size == 1)
        composeTestRule.onNodeWithTag("CategoryItem#1").assertExists()
        composeTestRule.onRoot().captureToImage()


    }

}

private fun ComposeContentTestRule.goToCategoryScreen() {
    this.onRoot().performTouchInput { swipeRight() }
    this.onNodeWithText("Category").performClick()
}

fun createDi(categoryRepository: CategoryRepository): DI {
    val testDI by DI.lazy {
        bind<CategoryRepository>() with provider { categoryRepository }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseRepository>() with provider { TestExpenseRepository() }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }
    }
    return testDI
}


class TestCategoryRepository : CategoryRepository {
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

class TestExpenseRepository : ExpenseRepository {
    private val storage = mutableMapOf<Long, Category>()
    private val idGenerator = IdGenerator()

    override fun remove(expenseId: Long): Boolean {
        return true
    }

    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return emptyList()
    }

    override fun add(expense: Expense): Expense {
        return randomExpense()
    }

    override fun update(expense: Expense): Expense {
        return randomExpense()
    }

    override fun getById(expenseId: Long): Expense {
        return randomExpense()
    }

}

fun randomExpense(): Expense =
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