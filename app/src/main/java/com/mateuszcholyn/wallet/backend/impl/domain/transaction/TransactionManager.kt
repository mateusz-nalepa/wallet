package com.mateuszcholyn.wallet.backend.impl.domain.transaction

// TODO: przetestuj to jakoś XD
interface TransactionManager {
    suspend fun <T> runInTransaction(block: suspend () -> T): T

}

class EmptyTransactionManager : TransactionManager {
    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        block()
}
