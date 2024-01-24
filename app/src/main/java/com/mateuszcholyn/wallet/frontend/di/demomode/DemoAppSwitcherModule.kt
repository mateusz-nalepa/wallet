package com.mateuszcholyn.wallet.frontend.di.demomode

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeDisabled
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeEnabled
import com.mateuszcholyn.wallet.userConfig.demoMode.DemoModeConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DemoAppSwitcherModule {

    @Provides
    @Singleton
    fun provideDemoAppSwitcher(@ApplicationContext context: Context): DemoAppSwitcher =
        if (DemoModeConfig.isDemoModeEnabled(context)) {
            DemoModeEnabled()
        } else {
            DemoModeDisabled()
        }
}
