package com.mateuszcholyn.wallet.di.hilt

import android.content.Context
import com.mateuszcholyn.wallet.ui.util.DefaultThemePropertiesProvider
import com.mateuszcholyn.wallet.ui.util.ThemePropertiesProvider
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