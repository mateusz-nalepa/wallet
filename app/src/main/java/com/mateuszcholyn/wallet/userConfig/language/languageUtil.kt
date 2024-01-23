package com.mateuszcholyn.wallet.userConfig.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

enum class WalletLanguage(
    val language: String,
    val locale: Locale,
) {
    POLISH(
        "Polski",
        Locale("pl", "PL"),
    ),
    ENGLISH(
        "English",
        Locale("en", "US"),
    ),
    ITALIAN(
        "Italiano",
        Locale("it", "IT"),
    ),
}

private val supportedLocales =
    WalletLanguage
        .entries
        .toList()
        .map { it.locale }

object LocaleService {

    fun getCurrentAppLanguage(): Locale =
        getSystemLanguage()
            ?.takeIf { it in supportedLocales }
            ?: WalletLanguage.ENGLISH.locale

    private fun getSystemLanguage(): Locale? =
        AppCompatDelegate
            .getApplicationLocales()
            .takeIf { !it.isEmpty }
            ?.get(0)

    fun setApplicationLanguage(locale: Locale) {
        AppCompatDelegate
            .setApplicationLocales(LocaleListCompat.create(locale))
    }

}

