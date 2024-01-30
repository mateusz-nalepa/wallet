package com.mateuszcholyn.wallet.frontend.di.usecases

import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabaseV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.SqLiteTransactionManager
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltTransactionManager {

    @Provides
    @Singleton
    fun provideTransactionManager(
        appDatabaseV2: AppDatabaseV2,
    ): TransactionManager =
        SqLiteTransactionManager(
            appDatabaseV2 = appDatabaseV2,
        )
}