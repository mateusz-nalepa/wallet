package com.mateuszcholyn.wallet.tests.manager


fun initExpenseAppManager(scope: ExpenseAppManagerScope.() -> Unit): ExpenseAppManager {

    val expenseAppManagerScope = ExpenseAppManagerScope().apply(scope)
    val expenseAppDependencies = ExpenseAppDependencies()
    val useCases = ExpenseAppUseCases.createFrom(expenseAppDependencies)

    ExpenseAppInitializer(
        expenseAppManagerScope = expenseAppManagerScope,
        expenseAppDependencies = expenseAppDependencies,
        expenseAppUseCases = useCases,
    ).init()

    return ExpenseAppManager(
        expenseAppDependencies = expenseAppDependencies,
        expenseAppUseCases = useCases
    )
}

class ExpenseAppManager(
    val expenseAppDependencies: ExpenseAppDependencies,
    val expenseAppUseCases: ExpenseAppUseCases,
)