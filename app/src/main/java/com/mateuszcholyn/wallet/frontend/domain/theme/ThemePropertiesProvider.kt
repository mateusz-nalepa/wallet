package com.mateuszcholyn.wallet.frontend.domain.theme

import androidx.compose.material.Colors

interface ThemePropertiesProvider {
    fun provide(isSystemInDarkTheme: Boolean): ThemeProperties
}

data class ThemeProperties(
    val colors: Colors,
    val shouldUseDarkTheme: Boolean,
    val resolver: Resolver,
)

enum class Resolver {
    SYSTEM,
    DARK,
    LIGHT,
}
