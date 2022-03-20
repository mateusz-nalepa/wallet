package com.mateuszcholyn.wallet.ui.screen.settings.themedropdown

import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.di.ActivityProvider
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.util.ThemeProperties
import com.mateuszcholyn.wallet.util.enableGivenTheme
import org.kodein.di.compose.rememberInstance


@Composable
@ExperimentalMaterialApi
fun ChangeThemeFragment(themeProperties: ThemeProperties) {
    val currentContext = LocalContext.current
    val activityProvider: ActivityProvider by rememberInstance()

    val availableThemes = themeDropdownElements()
    var selectedTheme by remember { mutableStateOf(availableThemes.find { it.resolver == themeProperties.resolver }!!) }

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
                enableGivenTheme(currentContext, activityProvider.get(), selectedTheme.resolver)
                ProcessPhoenix.triggerRebirth(currentContext)
            },
            modifier = defaultButtonModifier,
    ) {
        Text(text = stringResource(R.string.useGivenTheme))
    }

}