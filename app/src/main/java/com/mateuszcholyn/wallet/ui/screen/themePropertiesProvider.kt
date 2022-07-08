package com.mateuszcholyn.wallet.ui.screen

import android.content.Context
import com.mateuszcholyn.wallet.util.darkmode.ThemeProperties
import com.mateuszcholyn.wallet.util.darkmode.resolveTheme

interface ThemePropertiesProvider {
    fun provide(isSystemInDarkTheme: Boolean): ThemeProperties
}

class DefaultThemePropertiesProvider(
        private val context: Context,
) : ThemePropertiesProvider {
    override fun provide(isSystemInDarkTheme: Boolean): ThemeProperties {
        return resolveTheme(context, isSystemInDarkTheme)
    }
}
