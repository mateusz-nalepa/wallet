package com.mateuszcholyn.wallet.frontend.view.util

import java.math.BigDecimal

const val EMPTY_STRING = ""

private const val BIG_DECIMAL_DEFAULT_SEPARATOR = "."


fun interface BigDecimalAsFormattedAmountFunction {
    fun invoke(bigDecimal: BigDecimal): String
}

fun BigDecimal.withTwoSignsPrecision(): BigDecimal =
    this.setScale(2, BigDecimal.ROUND_HALF_UP)

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
        .replace(BIG_DECIMAL_DEFAULT_SEPARATOR, priceFormatterParameters.separator)
}

// TODO: nie wiem o co chodzi z tą funkcją poniżej :D
fun String.toDoubleOrDefaultZero(): Double =
    kotlin.runCatching { this.toDouble() }
        .getOrDefault(0.0)

// TODO: user should be able to select his own currency and separator XD
// TODO: na formularzu dodawania dobrze jakby pole tekstowe miało: Kwota i symbol waluty XD

data class PriceFormatterParameters(
    val currencySymbol: String = "$",
    val separator: String = ",",
)
