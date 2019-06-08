package com.mateuszcholyn.wallet.util

fun Double.asAmount() = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
}