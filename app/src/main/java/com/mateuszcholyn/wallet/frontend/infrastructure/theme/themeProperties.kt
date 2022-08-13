package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import com.mateuszcholyn.wallet.frontend.domain.theme.Resolver
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemeProperties

fun lightThemeProperties(resolver: Resolver): ThemeProperties =
    ThemeProperties(
        colors = lightColors(),
        shouldUseDarkTheme = false,
        resolver = resolver,
    )

fun darkThemeProperties(resolver: Resolver): ThemeProperties =
    ThemeProperties(
        colors = darkColors(),
        shouldUseDarkTheme = true,
        resolver = resolver,
    )