package com.mateuszcholyn.wallet.userConfig.language

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.mateuszcholyn.wallet.R
import java.util.Locale

enum class WalletLanguage(
    @StringRes
    val nameKey: Int,
    val locale: Locale,
) {
    POLISH(
        R.string.settings_language_polish,
        Locale("pl", "PL"),
    ),
    ENGLISH(
        R.string.settings_language_english,
        Locale("en", "US"),
    ),
    ITALIAN(
        R.string.settings_language_italiano,
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

