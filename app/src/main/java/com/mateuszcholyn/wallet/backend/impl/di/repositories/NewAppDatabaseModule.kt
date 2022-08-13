package com.mateuszcholyn.wallet.backend.impl.di.repositories

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabaseV2
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabaseV2 =
        Room.databaseBuilder(
            context,
            AppDatabaseV2::class.java,
            "wallet_database.db",
        )
            .allowMainThreadQueries() // HODOR this should be fixed!!
            .build()
}


