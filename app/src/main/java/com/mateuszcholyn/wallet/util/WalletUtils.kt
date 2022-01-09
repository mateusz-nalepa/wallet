package com.mateuszcholyn.wallet.util

import android.text.Editable

const val ALL_CATEGORIES = "Wszystkie kategorie"

fun Double.asPrinteableAmount(): Double = run {
    "%.2f"
        .format(this)
        .replace(",", ".")
        .toDouble()
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)