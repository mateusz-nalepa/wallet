package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemeProperties
import com.mateuszcholyn.wallet.frontend.infrastructure.theme.enableGivenTheme
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier


@Composable
@ExperimentalMaterialApi
fun ChangeThemeFragment(
    themeProperties: ThemeProperties,
) {
    val currentContext = currentAppContext()

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
            enableGivenTheme(currentContext, selectedTheme.resolver)
            ProcessPhoenix.triggerRebirth(currentContext)
        },
        modifier = defaultButtonModifier,
    ) {
        Text(text = stringResource(R.string.useGivenTheme))
    }

}