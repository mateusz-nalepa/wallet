package com.mateuszcholyn.wallet.newTests.setup.di

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.config.AppDatabaseV2
import com.mateuszcholyn.wallet.di.usecases.NewAppDatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewAppDatabaseModule::class]
)
object TestNewAppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabaseV2 =
        Room
            .inMemoryDatabaseBuilder(context, AppDatabaseV2::class.java)
            .allowMainThreadQueries() // this should be fixed!
            .build()
}


