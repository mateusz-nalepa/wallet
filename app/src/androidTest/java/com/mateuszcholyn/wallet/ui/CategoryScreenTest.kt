package com.mateuszcholyn.wallet.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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

    @ExperimentalMaterialApi
    @Test
    fun myFirstTest() {
        // Start the app
        composeTestRule.setContent {
            withDI(di = TestDI) {
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

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }

}


val TestDI by DI.lazy {
    bind<CategoryRepository>() with provider { TestCategoryRepository() }
    bind<CategoryService>() with provider { CategoryService(instance()) }

    //Expense
    bind<ExpenseRepository>() with provider { TestExpenseRepository() }
    bind<ExpenseService>() with provider { ExpenseService(instance()) }
}


class TestCategoryRepository : CategoryRepository {
    override fun getAllOrderByUsageDesc(): List<Category> {
        return emptyList()
    }

    override fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> {
        return emptyList()
    }

    override fun remove(categoryId: Long): Boolean {
        return true
    }

    override fun add(category: Category): Category {
        return randomCategory()
    }
}

class TestExpenseRepository : ExpenseRepository {
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
