package com.mateuszcholyn.wallet.di

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.mateuszcholyn.wallet.ui.wellness.InMemoryWellnessRepository
import com.mateuszcholyn.wallet.ui.wellness.WellnessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideWellnessRepository(): WellnessRepository {
        return InMemoryWellnessRepository()
    }

}