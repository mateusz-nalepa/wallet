package com.mateuszcholyn.wallet.frontend.view.screen.settings.demomode

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier

@Composable
fun DemoModeFragment(
    demoButtonText: String,
    switchContextFunction: () -> Unit,
) {

    Button(
        onClick = { switchContextFunction() },
        modifier = defaultButtonModifier,
    ) {
        Text(text = demoButtonText)
    }
}