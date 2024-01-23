package com.mateuszcholyn.wallet.frontend.view.screen.settings.language

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.userConfig.UserConfigProvider
import com.mateuszcholyn.wallet.userConfig.theme.ThemeChanger
import com.mateuszcholyn.wallet.userConfig.theme.WalletThemeSelectedByUser


@Composable
@ExperimentalMaterialApi
fun ChangeThemeFragment() {
    val availableThemes = themeDropdownElements()
    val userContextProvider = UserConfigProvider(currentAppContext())

    var selectedTheme by remember { mutableStateOf(availableThemes.find { it.walletTheme.themeName == WalletThemeSelectedByUser.value }!!) }
    WalletDropdown(
        dropdownName = stringResource(R.string.theme),
        selectedElement = selectedTheme,
        availableElements = availableThemes,
        onItemSelected = {
            selectedTheme = it
            ThemeChanger.enableGivenTheme(userContextProvider, it.walletTheme.themeName)
        },
    )
}
