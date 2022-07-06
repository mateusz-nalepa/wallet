package com.mateuszcholyn.wallet.di

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.mateuszcholyn.wallet.ui.wellness.Clicker
import com.mateuszcholyn.wallet.ui.wellness.DefaultClicker
import com.mateuszcholyn.wallet.ui.wellness.InMemoryWellnessRepository
import com.mateuszcholyn.wallet.ui.wellness.WellnessRepository
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
    fun provideClicker(@ApplicationContext context: Context): Clicker {
        return DefaultClicker(context)
    }

    @Provides
    @Singleton
    fun provideWellnessRepository(): WellnessRepository {
        return InMemoryWellnessRepository()
    }

}