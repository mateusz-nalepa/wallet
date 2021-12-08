package com.mateuszcholyn.wallet.ui.dummy

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes: Shapes = Shapes(
        small = RoundedCornerShape(percent = 50),
        medium = RoundedCornerShape(0f),
        large = CutCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomStart = 16.dp,
                bottomEnd = 0.dp
        )
)