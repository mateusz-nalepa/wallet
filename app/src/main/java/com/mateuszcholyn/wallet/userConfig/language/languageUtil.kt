package com.mateuszcholyn.wallet.userConfig.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import java.util.Locale


object LocaleService {

    @Composable
    fun getCurrentLocale(): Locale =
        ConfigurationCompat
            .getLocales(LocalConfiguration.current)
            .get(0)
            ?: Locale.getDefault()

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

