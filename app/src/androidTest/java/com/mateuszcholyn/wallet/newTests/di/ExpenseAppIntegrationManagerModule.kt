package com.mateuszcholyn.wallet.newTests.di

import com.mateuszcholyn.wallet.newTests.ExpenseAppIntegrationManager
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExpenseAppIntegrationManagerModule {

    @Provides
    @Singleton
    fun provideExpenseAppIntegrationManager(
        expenseRepositoryV2: ExpenseRepositoryV2,
    ): ExpenseAppIntegrationManager =
        ExpenseAppIntegrationManager(
            expenseRepositoryV2 = expenseRepositoryV2,
        )

}


