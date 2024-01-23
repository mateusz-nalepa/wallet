package com.mateuszcholyn.wallet.userConfig.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

//LocaleService.setApplicationLocale(locale)

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

object LocaleService {

    fun getCurrentLocaleV2(): Locale =
        AppCompatDelegate
            .getApplicationLocales()
            .takeIf { !it.isEmpty }
            ?.get(0)
            ?: Locale.getDefault()

    fun setApplicationLocale(locale: Locale) {
        AppCompatDelegate
            .setApplicationLocales(LocaleListCompat.create(locale))
    }

}

