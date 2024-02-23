package com.mateuszcholyn.wallet.frontend.view.util

import java.math.BigDecimal

const val EMPTY_STRING = ""

fun BigDecimal.asFormattedAmount(): BigDecimal =
    this.setScale(2, BigDecimal.ROUND_HALF_UP)

fun BigDecimal.asPrintableAmount(): String = run {
    asFormattedAmount()
        .toString() + "$"
}

fun BigDecimal.asPrintableAmountWithoutDollar(): String = run {
    asFormattedAmount()
        .toString()
}

fun String.toDoubleOrDefaultZero(): Double =
    kotlin.runCatching { this.toDouble() }
        .getOrDefault(0.0)
