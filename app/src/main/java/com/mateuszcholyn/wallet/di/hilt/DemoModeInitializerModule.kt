package com.mateuszcholyn.wallet.di.hilt

import com.mateuszcholyn.wallet.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.domain.demomode.DemoModeInitializer
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DemoModeInitializerModule {

    @Provides
    @Singleton
    fun provideDemoModeInitializer(
        demoAppSwitcher: DemoAppSwitcher,
        expenseAppUseCases: ExpenseAppUseCases,
    ): DemoModeInitializer =
        DemoModeInitializer(
            demoAppSwitcher = demoAppSwitcher,
            expenseAppUseCases = expenseAppUseCases,
        ).also {
            it.init()
        }

}