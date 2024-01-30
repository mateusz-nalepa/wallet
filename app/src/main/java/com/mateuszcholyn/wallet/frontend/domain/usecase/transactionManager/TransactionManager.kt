package com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager

import androidx.room.withTransaction
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabaseV2


// TODO: poprzenosc impl w dobre miejsca
interface TransactionManager {
    suspend fun <T> runInTransaction(block: suspend () -> T): T

}

class EmptyTransactionManager : TransactionManager {
    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        block()
}


class SqLiteTransactionManager(
    private val appDatabaseV2: AppDatabaseV2
) : TransactionManager {

    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        appDatabaseV2.withTransaction {
            block()
        }

}
