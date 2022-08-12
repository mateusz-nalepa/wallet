package com.mateuszcholyn.wallet.di.hilt

import android.content.Context
import com.mateuszcholyn.wallet.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.domain.demomode.DemoModeDisabled
import com.mateuszcholyn.wallet.domain.demomode.DemoModeEnabled
import com.mateuszcholyn.wallet.util.demomode.isInDemoMode
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
        if (isInDemoMode(context)) {
            DemoModeEnabled(context)
        } else {
            DemoModeDisabled(context)
        }

}
