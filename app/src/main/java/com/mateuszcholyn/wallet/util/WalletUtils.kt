package com.mateuszcholyn.wallet.util

const val ALL_CATEGORIES = "Wszystkie kategorie"

fun Double.asFormattedAmount(): Double = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
}

fun Double.asPrinteableAmount(): String = run {
    asFormattedAmount()
            .toString() + " zł"
}
