package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemeProperties
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemePropertiesProvider

class DefaultThemePropertiesProvider(
    private val context: Context,
) : ThemePropertiesProvider {
    override fun provide(isSystemInDarkTheme: Boolean): ThemeProperties =
        resolveTheme(context, isSystemInDarkTheme)
}


