package com.mateuszcholyn.wallet.frontend.di.demomode

import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
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