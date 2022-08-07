package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.tests.manager.setupunittest.createFrom


fun initExpenseAppManager(scope: ExpenseAppManagerScope.() -> Unit): ExpenseAppManager {

    val expenseAppManagerScope = ExpenseAppManagerScope().apply(scope)
    val expenseAppDependencies = ExpenseAppDependencies()
    val useCases = createFrom(expenseAppDependencies)

    ExpenseAppUnitInitializer(
        expenseAppManagerScope = expenseAppManagerScope,
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
