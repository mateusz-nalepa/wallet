package com.mateuszcholyn.wallet.ui.dummy

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val H6: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
)
val Body2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
)
val Button: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
)

val Typography: Typography = Typography(
        h6 = H6,
        body2 = Body2,
        button = Button,
//        defaultFontFamily = FontFamily(Font(R.font.roboto_mono))
        // Using default values for subtitle1, caption, etc.
)