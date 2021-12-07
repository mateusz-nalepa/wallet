package com.mateuszcholyn.wallet.ui.dummy

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Navy500 = Color(0xFF64869B)
val Navy700 = Color(0xFF37596D)
val Navy900 = Color(0xFF073042)
val Green300 = Color(0xFF3DDC84)
val Green900 = Color(0xFF00A956)

val LightColors = lightColors(
        primary = Navy700,
        primaryVariant = Navy900,
        secondary = Green300,
        secondaryVariant = Green900
        // Using default values for onPrimary, surface, error, etc.
)

val DarkColors = darkColors(
        primary = Navy500,
        primaryVariant = Navy900,
        secondary = Green300
        // secondaryVariant == secondary in dark theme
)