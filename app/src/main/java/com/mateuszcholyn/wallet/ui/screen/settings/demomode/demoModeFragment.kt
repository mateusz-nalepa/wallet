package com.mateuszcholyn.wallet.ui.screen.settings.demomode

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.util.appContext.currentAppContext
import org.kodein.di.compose.rememberInstance

@Composable
fun DemoModeFragment() {
    val currentContext = currentAppContext()
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