package com.mateuszcholyn.wallet.backend.impl.domain.util

fun <KEY, ENTRY> MutableMap<KEY, ENTRY>.removeAll(
    keyFromEntry: (ENTRY) -> KEY,
) {
    val keys = this.values.toList().map { keyFromEntry(it) }

    keys.forEach { key ->
        this.remove(key)
    }
}
