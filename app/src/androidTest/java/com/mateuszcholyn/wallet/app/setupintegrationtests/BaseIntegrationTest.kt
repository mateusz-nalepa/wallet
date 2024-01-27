package com.mateuszcholyn.wallet.app.setupintegrationtests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.manager.ExpenseAppInitializer
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.ExpenseAppManagerScope
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
        println("Integration tests are not working without this XD")
    }

}

