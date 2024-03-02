package com.mateuszcholyn.wallet.frontend.view.util

import java.math.BigDecimal

const val EMPTY_STRING = ""

fun BigDecimal.withTwoSignsPrecision(): BigDecimal =
    this.setScale(2, BigDecimal.ROUND_HALF_UP)

fun BigDecimal.asPrintableAmount(): String = run {
    asPrintableAmountWithoutCurrencySymbol() + "$"
}

fun BigDecimal.asPrintableAmountWithoutCurrencySymbol(): String = run {
    withTwoSignsPrecision()
        .toString()
        .replace(".", ",")
}

fun BigDecimal.asPrintableAmount(
    priceFormatterParameters: PriceFormatterParameters,
): String = run {
    asPrintableAmountWithoutCurrencySymbol(priceFormatterParameters) + priceFormatterParameters.currencySymbol
}

fun BigDecimal.asPrintableAmountWithoutCurrencySymbol(
    priceFormatterParameters: PriceFormatterParameters,
): String = run {
    withTwoSignsPrecision()
        .toString()
        .replace(".", priceFormatterParameters.separator)
}

// TODO: nie wiem o co chodzi z tą funkcją poniżej :D
fun String.toDoubleOrDefaultZero(): Double =
    kotlin.runCatching { this.toDouble() }
        .getOrDefault(0.0)


data class PriceFormatterParameters(
    val currencySymbol: String = "$",
    val separator: String = ",",
)
