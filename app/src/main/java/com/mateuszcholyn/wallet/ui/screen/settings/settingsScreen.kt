package com.mateuszcholyn.wallet.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.ui.dropdown.DropdownElement
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.Resolver
import com.mateuszcholyn.wallet.util.ThemeProperties
import com.mateuszcholyn.wallet.util.enableGivenTheme
import org.kodein.di.compose.rememberInstance

data class ThemeDropdownElement(
        override val name: String,
        val resolver: Resolver,
) : DropdownElement


@ExperimentalMaterialApi
@Composable
fun SettingsScreen(themeProperties: ThemeProperties) {
    val currentContext = LocalContext.current
    val demoAppEnabledProvider: DemoAppEnabledProvider by rememberInstance()

    val availableThemes =
            listOf(
                    ThemeDropdownElement(
                            name = stringResource(R.string.useSystemTheme),
                            resolver = Resolver.SYSTEM,
                    ),
                    ThemeDropdownElement(
                            name = stringResource(R.string.lightTheme),
                            resolver = Resolver.LIGHT,

                            ),
                    ThemeDropdownElement(
                            name = stringResource(R.string.darkTheme),
                            resolver = Resolver.DARK,
                    ))

    var selectedTheme by remember { mutableStateOf(availableThemes.find { it.resolver == themeProperties.resolver }!!) }

    Column(modifier = defaultModifier) {
        WalletDropdown(
                dropdownName = stringResource(R.string.theme),
                selectedElement = selectedTheme,
                availableElements = availableThemes,
                onItemSelected = {
                    selectedTheme = it
                },
        )
        Button(
                onClick = {
                    enableGivenTheme(selectedTheme.resolver)
                    ProcessPhoenix.triggerRebirth(currentContext)
                },
                modifier = defaultButtonModifier,
        ) {
            Text(text = stringResource(R.string.useGivenTheme))
        }
        Divider()
        Divider()
        Divider()
        Divider()
        Divider()
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
