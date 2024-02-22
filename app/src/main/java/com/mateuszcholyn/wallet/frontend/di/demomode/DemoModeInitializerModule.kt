package com.mateuszcholyn.wallet.frontend.di.demomode

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.domain.appstate.DemoModeAppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.frontend.infrastructure.appstate.HiltDemoModeAppIsConfigured
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
    fun provideDemoModeAppIsConfigured(
        allBackendServices: AllBackendServices,
        demoModeInitializer: DemoModeInitializer,
    ): DemoModeAppIsConfigured =
        HiltDemoModeAppIsConfigured(
            allBackendServices = allBackendServices,
            demoModeInitializer = demoModeInitializer,
        )

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