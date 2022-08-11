package com.mateuszcholyn.wallet.ui.screen.dummy

import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import java.math.BigDecimal
import java.math.BigInteger

//category_id, name
private val allCategories = """""".trimIndent()

//expense_id,amount,description,date,fk_category_id
private val allExpenses = """""".trimIndent()

fun odpalMigracje(
    expenseService: ExpenseService,
    categoryService: CategoryService,
) {
    TODO("you need to fix me!!")
//    val objectMapper = ObjectMapper().findAndRegisterModules()
//
//
//    val categories = objectMapper.readValue(allCategories, CategoriesFromDb::class.java)
//    val expenses = objectMapper.readValue(allExpenses, ExpensesFromDb::class.java)
//
//
//    val allExpensesRemoved = expenseService.removeAll()
//    val allCategoriesRemoved = categoryService.removeAll()
//
//    val newSavedCategories =
//        categories.categories
//            .map { categoryFromDb ->
//                val added =
//                    categoryService.add(
//                        Category(
//                            id = categoryFromDb.category_id.toLong(),
//                            name = categoryFromDb.name,
//                        )
//                    )
//
//                SavedCategoryFromDb(
//                    newCategoryId = added.id,
//                    oldCategoryId = categoryFromDb.category_id.toLong(),
//                    name = added.name,
//                )
//            }
//
//
//    val xdd = categoryService.getAll()
//
//    expenses.expenses.forEach { expenseFromDb ->
//
//        expenseService.saveExpense(Expense(
//            amount = expenseFromDb.amount,
//            date = expenseFromDb.date.toLong().toLocalDateTime(),
//            description = expenseFromDb.description,
//            category = newSavedCategories.first { it.oldCategoryId == expenseFromDb.fk_category_id.toLong() }
//                .let { saved ->
//                    ExistingCategory(
//                        id = saved.newCategoryId,
//                        name = saved.name
//                    )
//                }
//        ))
//
//    }

}


data class CategoriesFromDb(
    val categories: List<CategoryFromDb>
)


data class CategoryFromDb(
    val category_id: Int,
    val name: String,
)


data class ExpensesFromDb(
    val expenses: List<ExpenseFromDb>
)

data class ExpenseFromDb(
    val amount: BigDecimal,
    val date: BigInteger,
    val description: String,
    val expense_id: Int,
    val fk_category_id: Int
)


data class SavedCategoryFromDb(
    val newCategoryId: Long,
    val oldCategoryId: Long,
    val name: String,
)