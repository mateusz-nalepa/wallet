package com.mateuszcholyn.wallet.frontend.view.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.settings.demomode.DemoModeFragment
import com.mateuszcholyn.wallet.frontend.view.screen.settings.language.ChangeThemeFragment
import com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown.ChangeLanguageFragment
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
@ExperimentalMaterialApi
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = currentAppContext()
    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
        ChangeThemeFragment()
        ChangeLanguageFragment()
        Divider()
        DemoModeFragment(
            demoButtonText = stringResource(settingsViewModel.demoAppSwitcher().buttonMessageKey()),
            switchContextFunction = { settingsViewModel.demoAppSwitcher().switch(context) }
        )
    }
}
