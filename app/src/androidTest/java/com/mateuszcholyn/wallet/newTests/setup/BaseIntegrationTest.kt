package com.mateuszcholyn.wallet.newTests.setup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppInitializer
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManagerScope
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
open class BaseIntegrationTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var expenseRepositoryV2: ExpenseRepositoryV2

    @Inject
    lateinit var integrationManager: ExpenseAppIntegrationManager

    @Before
    fun init() {
        hiltRule.inject()
    }

    fun initExpenseAppManager(scope: ExpenseAppManagerScope.() -> Unit): ExpenseAppManager {
        val expenseAppManagerScope = ExpenseAppManagerScope().apply(scope)

        ExpenseAppInitializer(
            expenseAppManagerScope = expenseAppManagerScope,
            expenseAppUseCases = integrationManager.expenseAppUseCases(),
        ).init()

        return ExpenseAppManager(
            expenseAppDependencies = integrationManager.dependencies(),
            expenseAppUseCases = integrationManager.expenseAppUseCases(),
        )
    }

    @Test
    fun pass() {
        println("All Tests is not working without this XD")
    }

}

