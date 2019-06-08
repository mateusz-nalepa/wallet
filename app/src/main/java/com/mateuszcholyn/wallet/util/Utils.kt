package com.mateuszcholyn.wallet.util

fun Double.asAmount() = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
}

fun String.asShortCategoryName(): String {
    return if (this.length > 14) {
        this.substring(0, 11) + " ..."
    } else {
        this
    }
}