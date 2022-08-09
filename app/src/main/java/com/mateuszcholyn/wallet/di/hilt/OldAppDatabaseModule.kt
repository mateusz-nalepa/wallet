package com.mateuszcholyn.wallet.di.hilt

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.config.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OldAppQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewAppQualifier


@Module
@InstallIn(SingletonComponent::class)
object OldAppDatabaseModule {

    @Provides
    @OldAppQualifier
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database.db",
        )
            .allowMainThreadQueries() // TODO this should be fixed!!
            .build()
}


