package com.mateuszcholyn.wallet.util

const val ALL_CATEGORIES = "Wszystkie kategorie"

fun Double.asPrinteableAmount(): Double = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
}
