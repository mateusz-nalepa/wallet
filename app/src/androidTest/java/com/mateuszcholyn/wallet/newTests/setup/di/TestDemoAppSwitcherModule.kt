package com.mateuszcholyn.wallet.newTests.setup.di

import android.content.Context
import com.mateuszcholyn.wallet.di.hilt.DemoAppSwitcherModule
import com.mateuszcholyn.wallet.domain.DemoAppSwitcher
import com.mateuszcholyn.wallet.domain.DemoModeDisabled
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DemoAppSwitcherModule::class]
)
object TestDemoAppSwitcherModule {

    @Provides
    @Singleton
    fun provideDemoAppSwitcher(@ApplicationContext context: Context): DemoAppSwitcher =
//        if (isInDemoMode(context)) {
//            DemoModeEnabled(context)
//        } else {
        DemoModeDisabled(context)
//        }

}
