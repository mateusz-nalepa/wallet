package com.mateuszcholyn.wallet.util

import java.math.BigDecimal

const val ALL_CATEGORIES = "Wszystkie kategorie"


fun BigDecimal.asFormattedAmount(): BigDecimal =
        this.setScale(2, BigDecimal.ROUND_HALF_UP)

fun BigDecimal.asPrinteableAmount(): String = run {
    asFormattedAmount()
            .toString() + " zł"
}