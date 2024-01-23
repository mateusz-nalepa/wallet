package com.mateuszcholyn.wallet.frontend.di.demomode

import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeDisabled
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeEnabled
import com.mateuszcholyn.wallet.userConfig.demoMode.DemoModeStaticConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DemoAppSwitcherModule {

    @Provides
    @Singleton
    fun provideDemoAppSwitcher(): DemoAppSwitcher =
        if (DemoModeStaticConfig.isDemoModeEnabled()) {
            DemoModeEnabled()
        } else {
            DemoModeDisabled()
        }
}
