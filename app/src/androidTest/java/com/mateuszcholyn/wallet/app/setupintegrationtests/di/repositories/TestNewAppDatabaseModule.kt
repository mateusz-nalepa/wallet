package com.mateuszcholyn.wallet.app.setupintegrationtests.di.repositories

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.backend.impl.di.repositories.NewAppDatabaseModule
import com.mateuszcholyn.wallet.backend.impl.di.repositories.addDatabaseMigrations
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabase
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
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .addDatabaseMigrations()
            .build()
}


