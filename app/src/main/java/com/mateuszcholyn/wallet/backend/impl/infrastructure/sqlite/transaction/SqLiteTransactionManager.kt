package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.transaction

import androidx.room.withTransaction
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabase

class SqLiteTransactionManager(
    private val appDatabase: AppDatabase
) : TransactionManager {

    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        appDatabase.withTransaction {
            block()
        }

}
