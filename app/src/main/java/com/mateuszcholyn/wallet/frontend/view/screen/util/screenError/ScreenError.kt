package com.mateuszcholyn.wallet.frontend.view.screen.util.screenError

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenError(errorMsg: String) {
    Text(text = "Error: $errorMsg")
}
