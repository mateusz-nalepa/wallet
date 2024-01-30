package com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun provideIODispatcher(): CoroutineDispatcher

}

class DefaultDispatcherProvider : DispatcherProvider {
    override fun provideIODispatcher(): CoroutineDispatcher =
        Dispatchers.IO

}