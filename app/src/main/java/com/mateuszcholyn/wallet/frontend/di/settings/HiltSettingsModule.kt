package com.mateuszcholyn.wallet.frontend.di.settings

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemePropertiesProvider
import com.mateuszcholyn.wallet.frontend.infrastructure.theme.DefaultThemePropertiesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltSettingsModule {

    @Provides
    @Singleton
    fun provideThemePropertiesProvider(
        @ApplicationContext context: Context,
    ): ThemePropertiesProvider =
        DefaultThemePropertiesProvider(context)

}