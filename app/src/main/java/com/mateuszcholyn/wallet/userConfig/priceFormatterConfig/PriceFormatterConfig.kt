package com.mateuszcholyn.wallet.userConfig.priceFormatterConfig

import android.content.Context
import androidx.core.content.edit
import com.mateuszcholyn.wallet.frontend.view.util.PriceFormatterParameters
import com.mateuszcholyn.wallet.userConfig.language.LocaleService
import com.mateuszcholyn.wallet.userConfig.language.WalletLanguage


private const val DEFAULT_APP_CURRENCY_SYMBOL = Typography.dollar.toString()

object PriceFormatterDefaultAmountSymbolResolver {
    fun getDefaultPriceSymbol(): String =
        when (LocaleService.getCurrentAppLanguage()) {
            WalletLanguage.ENGLISH.locale -> DEFAULT_APP_CURRENCY_SYMBOL
            WalletLanguage.POLISH.locale -> "zł"
            WalletLanguage.ITALIAN.locale -> Typography.euro.toString()
            WalletLanguage.HODOR.locale -> DEFAULT_APP_CURRENCY_SYMBOL
            else -> DEFAULT_APP_CURRENCY_SYMBOL
        }
}

object PriceFormatterDefaultAmountSeparator {
    fun getDefaultPriceSeparator(): String =
        when (LocaleService.getCurrentAppLanguage()) {
            WalletLanguage.ENGLISH.locale -> "."
            WalletLanguage.POLISH.locale -> ","
            WalletLanguage.ITALIAN.locale -> ","
            WalletLanguage.HODOR.locale -> "."
            else -> "."
        }
}

object PriceFormatterParametersConfig {
    private const val SHARED_PREFERENCES_NAME = "walletPreferences"
    private const val AMOUNT_SYMBOL = "amountSymbol"
    private const val AMOUNT_SEPARATOR = "amountSeparator"

    fun setAmountSymbol(context: Context, value: String) {
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit(commit = true) {
                putString(AMOUNT_SYMBOL, value)
            }
    }

    fun setAmountSeparator(context: Context, value: String) {
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit(commit = true) {
                putString(AMOUNT_SEPARATOR, value)
            }
    }

    // TODO: użycia tego nie powinny być w remember? XD
    fun getPriceFormatterParameters(context: Context): PriceFormatterParameters =
           PriceFormatterParameters(
            currencySymbol = getAmountSymbol(context),
            separator = getAmountSeparator(context),
        )

    private fun getAmountSymbol(context: Context): String =
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(
                AMOUNT_SYMBOL,
                PriceFormatterDefaultAmountSymbolResolver.getDefaultPriceSymbol()
            )
            ?: PriceFormatterDefaultAmountSymbolResolver.getDefaultPriceSymbol()

    private fun getAmountSeparator(context: Context): String =
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(
                AMOUNT_SEPARATOR,
                PriceFormatterDefaultAmountSeparator.getDefaultPriceSeparator()
            )
            ?: PriceFormatterDefaultAmountSeparator.getDefaultPriceSeparator()
}