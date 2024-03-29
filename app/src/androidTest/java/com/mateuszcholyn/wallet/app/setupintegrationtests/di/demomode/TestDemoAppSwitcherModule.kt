package com.mateuszcholyn.wallet.app.setupintegrationtests.di.demomode

import android.content.Context
import com.mateuszcholyn.wallet.frontend.di.demomode.DemoAppSwitcherModule
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeDisabled
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
    fun provideDemoAppSwitcher(
        @ApplicationContext context: Context,
    ): DemoAppSwitcher =
        /**
         * For Integration Tests we want to be sure, that we are not using InMemoryImplementations
         */
        DemoModeDisabled()

}
