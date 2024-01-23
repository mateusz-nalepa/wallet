package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.userConfig.language.LocaleService


@Composable
@ExperimentalMaterialApi
fun ChangeLanguageFragment() {
    val availableLanguages = languageDropdownElements()
    var selectedLanguage by remember { mutableStateOf(availableLanguages[0]) }
    WalletDropdown(
        dropdownName = stringResource(R.string.language),
        selectedElement = selectedLanguage,
        availableElements = availableLanguages,
        onItemSelected = {
            selectedLanguage = it
            LocaleService.setApplicationLocale(it.walletLanguage.locale)
        },
    )
}
