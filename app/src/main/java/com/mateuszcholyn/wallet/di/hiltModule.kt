package com.mateuszcholyn.wallet.di

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.mateuszcholyn.wallet.domain.DemoAppSwitcher
import com.mateuszcholyn.wallet.domain.DemoModeDisabled
import com.mateuszcholyn.wallet.domain.DemoModeEnabled
import com.mateuszcholyn.wallet.ui.screen.DefaultThemePropertiesProvider
import com.mateuszcholyn.wallet.ui.screen.ThemePropertiesProvider
import com.mateuszcholyn.wallet.ui.wellness.Clicker
import com.mateuszcholyn.wallet.ui.wellness.DefaultClicker
import com.mateuszcholyn.wallet.ui.wellness.InMemoryWellnessRepository
import com.mateuszcholyn.wallet.ui.wellness.WellnessRepository
import com.mateuszcholyn.wallet.util.demomode.isInDemoMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideClicker(@ApplicationContext context: Context): Clicker =
            DefaultClicker(context)

    @Provides
    @Singleton
    fun provideWellnessRepository(): WellnessRepository =
            InMemoryWellnessRepository()

    @Provides
    @Singleton
    fun provideThemePropertiesProvider(@ApplicationContext context: Context): ThemePropertiesProvider =
            DefaultThemePropertiesProvider(context)

    @Provides
    @Singleton
    fun provideDemoAppSwitcher(@ApplicationContext context: Context): DemoAppSwitcher =
            if (isInDemoMode(context)) {
                DemoModeEnabled(context)
            } else {
                DemoModeDisabled(context)
            }
}