package com.mateuszcholyn.wallet.frontend.di.appstate

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer
import com.mateuszcholyn.wallet.frontend.infrastructure.appstate.HiltAppIsConfigured
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppStateModule {

    @Provides
    @Singleton
    fun provideDemoModeInitializer(
        allBackendServices: AllBackendServices,
        demoModeInitializer: DemoModeInitializer,
    ): AppIsConfigured =
        HiltAppIsConfigured(
            allBackendServices = allBackendServices,
            demoModeInitializer = demoModeInitializer,
        )

}