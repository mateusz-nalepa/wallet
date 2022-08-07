package com.mateuszcholyn.wallet.newTests

import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class CustomAndroidTest : BaseAndroidTest() {

    @Test
    fun asdasd() {
        val allExpenses = expenseRepositoryV2.getAllExpenses()
    }

}