package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.util.localDateTimeUtils.today
import java.math.BigDecimal
import java.time.Instant

class ExpenseAppManagerScope {

    val categoriesScope = mutableListOf<CategoryScope>()

}

fun ExpenseAppManagerScope.category(scope: CategoryScope.() -> Unit): CategoryScope {
    val categoryScope = CategoryScope().apply(scope)
    categoriesScope.add(categoryScope)
    return categoryScope
}

class CategoryScope {
    lateinit var categoryId: CategoryId
    var categoryName = randomCategoryName()

    val expensesScope = mutableListOf<ExpenseScope>()
    val subCategoriesScope = mutableListOf<CategoryScope>()
}

fun CategoryScope.expense(scope: ExpenseScope.() -> Unit): ExpenseScope {
    val expenseScope = ExpenseScope().apply(scope)
    expensesScope.add(expenseScope)
    return expenseScope
}

fun CategoryScope.subCategory(scope: CategoryScope.() -> Unit): CategoryScope {
    val subCategoryScope = CategoryScope().apply(scope)
    subCategoriesScope.add(subCategoryScope)
    return subCategoryScope
}

class ExpenseScope {
    lateinit var expenseId: ExpenseId
    var amount: BigDecimal = randomAmount()
    var description: String = randomDescription()
    var paidAt: Instant = today()
}
