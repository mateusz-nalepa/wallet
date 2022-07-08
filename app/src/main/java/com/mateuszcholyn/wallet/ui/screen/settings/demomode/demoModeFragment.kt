package com.mateuszcholyn.wallet.ui.screen.settings.demomode

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier

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