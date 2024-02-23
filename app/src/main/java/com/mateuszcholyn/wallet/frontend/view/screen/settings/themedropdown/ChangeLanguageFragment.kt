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
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.userConfig.hodorLanguage.HodorLanguageConfig
import com.mateuszcholyn.wallet.userConfig.language.LocaleService
import com.mateuszcholyn.wallet.userConfig.language.WalletLanguage
import java.util.Locale


@Composable
@ExperimentalMaterialApi
fun ChangeLanguageFragment() {

    val availableLanguages =
        languageDropdownElements(
            HodorLanguageConfig.isHodorLanguageNotAvailable(currentAppContext())
        )
    var selectedLanguage by remember {
        mutableStateOf(LocaleService.getCurrentAppLanguage().toLanguageDropdownElement())
    }
    WalletDropdown(
        dropdownName = stringResource(R.string.settings_language),
        selectedElement = selectedLanguage,
        availableElements = availableLanguages,
        onItemSelected = {
            selectedLanguage = it
            LocaleService.setApplicationLanguage(it.walletLanguage.locale)
        },
    )
}

private fun Locale.toLanguageDropdownElement(): LanguageDropdownElement =
    when (this) {
        WalletLanguage.ENGLISH.locale -> WalletLanguage.ENGLISH
        WalletLanguage.POLISH.locale -> WalletLanguage.POLISH
        WalletLanguage.ITALIAN.locale -> WalletLanguage.ITALIAN
        WalletLanguage.HODOR.locale -> WalletLanguage.HODOR
        else -> WalletLanguage.ENGLISH
    }
        .toLanguageDropdownElement()