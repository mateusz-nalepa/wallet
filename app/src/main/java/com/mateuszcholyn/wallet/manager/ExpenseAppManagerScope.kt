package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.util.localDateTimeUtils.today
import java.math.BigDecimal
import java.time.LocalDateTime

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

}

fun CategoryScope.expense(scope: ExpenseScope.() -> Unit): ExpenseScope {
    val expenseScope = ExpenseScope().apply(scope)
    expensesScope.add(expenseScope)
    return expenseScope
}

class ExpenseScope {
    lateinit var expenseId: ExpenseId
    var amount: BigDecimal = randomAmount()
    var description: String = randomDescription()
    var paidAt: LocalDateTime = today()
}