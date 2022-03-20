package com.mateuszcholyn.wallet.ui.screen.settings.demomode

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import org.kodein.di.compose.rememberInstance

@Composable
fun DemoModeFragment() {
    val currentContext = LocalContext.current
    val demoAppEnabledProvider: DemoAppEnabledProvider by rememberInstance()
    Button(
            onClick = {
                demoAppEnabledProvider.changeContext(currentContext)
                ProcessPhoenix.triggerRebirth(currentContext)
            },
            modifier = defaultButtonModifier,
    ) {
        Text(text = demoAppEnabledProvider.buttonText())
    }
}