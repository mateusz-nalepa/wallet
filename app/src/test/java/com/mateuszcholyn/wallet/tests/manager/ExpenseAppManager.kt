package com.mateuszcholyn.wallet.tests.manager


fun initExpenseAppManager(scope: ExpenseAppManagerScope.() -> Unit): ExpenseAppManager {

    val expenseAppDependencies = ExpenseAppDependencies()

    return ExpenseAppManager(
        expenseAppDependencies = expenseAppDependencies,
        expenseAppUseCases = ExpenseAppUseCases.createFrom(expenseAppDependencies)
    )
}


class ExpenseAppManagerScope


class ExpenseAppManager(
    val expenseAppDependencies: ExpenseAppDependencies,
    val expenseAppUseCases: ExpenseAppUseCases,
)