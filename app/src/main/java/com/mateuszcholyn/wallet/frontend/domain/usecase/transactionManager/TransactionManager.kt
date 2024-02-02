package com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager

import androidx.room.withTransaction
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabase


// TODO: poprzenosc impl w dobre miejsca
interface TransactionManager {
    suspend fun <T> runInTransaction(block: suspend () -> T): T

}

class EmptyTransactionManager : TransactionManager {
    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        block()
}


class SqLiteTransactionManager(
    private val appDatabase: AppDatabase
) : TransactionManager {

    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        appDatabase.withTransaction {
            block()
        }

}
