package com.mateuszcholyn.wallet.frontend.view.screen.util.preview

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mateuszcholyn.wallet.userConfig.theme.LocalWalletThemeComposition
import com.mateuszcholyn.wallet.userConfig.theme.WalletTheme

@Composable
fun SetContentOnDarkPreview(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.DARK.themeName) }) {
        MaterialTheme(colors = darkColors()) {
            content()
        }
    }
}

@Composable
fun SetContentOnLightPreview(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.LIGHT.themeName) }) {
        MaterialTheme(colors = lightColors()) {
            content()
        }
    }
}
