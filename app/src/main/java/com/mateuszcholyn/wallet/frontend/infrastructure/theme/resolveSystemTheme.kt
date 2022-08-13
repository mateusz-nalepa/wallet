package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import com.mateuszcholyn.wallet.frontend.domain.theme.Resolver
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemeProperties

fun resolveSystemTheme(isSystemInDarkTheme: Boolean): ThemeProperties =
    when (isSystemInDarkTheme) {
        true -> darkThemeProperties(Resolver.SYSTEM)
        false -> lightThemeProperties(Resolver.SYSTEM)
    }
