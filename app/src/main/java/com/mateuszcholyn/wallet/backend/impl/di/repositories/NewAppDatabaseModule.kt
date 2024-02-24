package com.mateuszcholyn.wallet.backend.impl.di.repositories

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabase
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.MIGRATION_1_2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewAppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "wallet_database.db",
            )
            .addDatabaseMigrations()
            .build()
}

fun RoomDatabase.Builder<AppDatabase>.addDatabaseMigrations(): RoomDatabase.Builder<AppDatabase> =
    addMigrations(
        MIGRATION_1_2,
        MIGRATION_2_3,
    )

