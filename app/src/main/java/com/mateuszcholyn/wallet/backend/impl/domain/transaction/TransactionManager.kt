package com.mateuszcholyn.wallet.backend.impl.domain.transaction

interface TransactionManager {
    suspend fun <T> runInTransaction(block: suspend () -> T): T

}

class EmptyTransactionManager : TransactionManager {
    override suspend fun <T> runInTransaction(block: suspend () -> T): T =
        try {
            block()
        } catch (t: Throwable) {
            throw TransactionManagerException(t)
        }
}

class TransactionManagerException(
    t: Throwable,
) : RuntimeException(t)
