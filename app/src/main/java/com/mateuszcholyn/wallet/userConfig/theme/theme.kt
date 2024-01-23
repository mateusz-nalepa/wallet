package com.mateuszcholyn.wallet.userConfig.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

enum class WalletTheme(val themeName: String) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light"),
}


val WalletThemeSelectedByUser = mutableStateOf<String?>(null)
val LocalWalletThemeComposition = compositionLocalOf { WalletThemeSelectedByUser }

@Composable
fun resolveThemeColors(
    actualTheme: String?,
    isSystemInDarkTheme: Boolean,
): Colors =
    when (actualTheme) {
        WalletTheme.LIGHT.themeName -> lightColors()
        WalletTheme.DARK.themeName -> darkColors()
        WalletTheme.SYSTEM.themeName -> resolveSystemTheme(isSystemInDarkTheme)
        else -> resolveSystemTheme(isSystemInDarkTheme)
    }

private fun resolveSystemTheme(isSystemInDarkTheme: Boolean): Colors =
    if (isSystemInDarkTheme) {
        darkColors()
    } else {
        lightColors()
    }

fun resolveThemeName(
    userSelectedTheme: String?,
    isSystemInDarkTheme: Boolean,
): String {

    if (userSelectedTheme != null) {
        return userSelectedTheme
    }

    return when (isSystemInDarkTheme) {
        true -> WalletTheme.DARK.themeName
        false -> WalletTheme.LIGHT.themeName
    }
}
