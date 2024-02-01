package com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun ScreenLoading() {
    Row(
        modifier = defaultModifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading")
    }
}
