package com.mateuszcholyn.wallet.ui.dummy

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun MyTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
) {
    MaterialTheme(
            colors = darkTheme.resolveApplicationTheme(),
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}

private fun Boolean.resolveApplicationTheme(): Colors =
        if (this) {
            DarkColors
        } else {
            LightColors
        }