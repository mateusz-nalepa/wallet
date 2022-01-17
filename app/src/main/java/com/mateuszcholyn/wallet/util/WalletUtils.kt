package com.mateuszcholyn.wallet.util

const val ALL_CATEGORIES = "Wszystkie kategorie"

fun Double.asPrinteableAmount(): String = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
            .toString() + " zł"
}
