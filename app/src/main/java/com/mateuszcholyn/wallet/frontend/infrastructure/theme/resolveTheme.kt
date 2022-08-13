package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.theme.Resolver
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemeProperties
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted
import com.mateuszcholyn.wallet.frontend.infrastructure.util.toFile

fun resolveTheme(ctx: Context, isSystemInDarkTheme: Boolean): ThemeProperties {
    if (mediaIsNotMounted()) {
        return lightThemeProperties(Resolver.LIGHT)
    }

    return runCatching {
        when {
            darkModeIsEnabled(ctx) -> darkThemeProperties(Resolver.DARK)
            lightModeIsEnabled(ctx) -> lightThemeProperties(Resolver.LIGHT)
            else -> resolveSystemTheme(isSystemInDarkTheme)
        }
    }
        .getOrDefault(lightThemeProperties(Resolver.SYSTEM))
}

private fun darkModeIsEnabled(ctx: Context): Boolean =
    darkModeFileExists(ctx) &&
            ctx
                .darkModeFilePath()
                .toFile()
                .readText() == Resolver.DARK.name


private fun lightModeIsEnabled(ctx: Context): Boolean =
    darkModeFileExists(ctx) &&
            ctx
                .darkModeFilePath()
                .toFile()
                .readText() == Resolver.LIGHT.name

private fun darkModeFileExists(ctx: Context): Boolean =
    !darkModeFileNotExists(ctx)

private fun darkModeFileNotExists(ctx: Context): Boolean =
    try {
        !ctx
            .darkModeFilePath()
            .toFile()
            .exists()
    } catch (t: Throwable) {
        true
    }