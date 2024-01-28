package com.mateuszcholyn.wallet.app.setupintegrationtests.di.repositories

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.backend.impl.di.repositories.NewAppDatabaseModule
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabaseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.MIGRATION_1_2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.MIGRATION_2_3
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
    ): AppDatabaseV2 =
        Room
            .inMemoryDatabaseBuilder(context, AppDatabaseV2::class.java)
            .allowMainThreadQueries() // this should be fixed!
            .addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
            )
            .build()
}


