package com.mateuszcholyn.wallet.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import org.kodein.di.compose.rememberInstance

@Composable
fun SettingsScreen() {
    val currentContext = LocalContext.current
    val demoAppEnabledProvider: DemoAppEnabledProvider by rememberInstance()

    Column(
            modifier = defaultModifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
                onClick = {
                    demoAppEnabledProvider.changeContext()
                    ProcessPhoenix.triggerRebirth(currentContext)
                },
                modifier = defaultButtonModifier,
        ) {
            Text(text = demoAppEnabledProvider.buttonText())
        }
    }
}
