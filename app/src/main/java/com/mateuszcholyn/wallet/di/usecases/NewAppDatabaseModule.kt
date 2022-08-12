package com.mateuszcholyn.wallet.di.usecases

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.config.AppDatabaseV2
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
            "database_v2.db",
        )
            .allowMainThreadQueries() // HODOR this should be fixed!!
            .build()
}


