package com.mateuszcholyn.wallet.di.hilt

import android.content.Context
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

@Module
@InstallIn(SingletonComponent::class)
object HiltWellnessModule {

    @Provides
    @Singleton
    fun provideClicker(@ApplicationContext context: Context): Clicker =
        DefaultClicker(context)

    @Provides
    @Singleton
    fun provideWellnessRepository(): WellnessRepository =
        InMemoryWellnessRepository()

}