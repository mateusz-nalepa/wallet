package com.mateuszcholyn.wallet.newTests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
open class BaseAndroidTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var expenseRepositoryV2: ExpenseRepositoryV2

    @Before
    fun init() {
        hiltRule.inject()
    }

}
