package com.mateuszcholyn.wallet.util

import java.math.BigDecimal

fun BigDecimal.asFormattedAmount(): BigDecimal =
        this.setScale(2, BigDecimal.ROUND_HALF_UP)

fun BigDecimal.asPrintableAmount(): String = run {
    asFormattedAmount()
            .toString() + " zł"
}

fun String.toDoubleOrDefaultZero(): Double =
        kotlin.runCatching { this.toDouble() }
                .getOrDefault(0.0)
