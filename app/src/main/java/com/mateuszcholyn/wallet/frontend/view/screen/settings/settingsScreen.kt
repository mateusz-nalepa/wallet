package com.mateuszcholyn.wallet.frontend.view.screen.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.domain.theme.ThemePropertiesProvider
import com.mateuszcholyn.wallet.frontend.view.screen.settings.demomode.DemoModeFragment
import com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown.ChangeThemeFragment
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePropertiesProvider: ThemePropertiesProvider,
    private val demoAppSwitcher: DemoAppSwitcher,
) : ViewModel() {

    fun getThemeProperties(isSystemInDarkTheme: Boolean) =
        themePropertiesProvider.provide(isSystemInDarkTheme)

    fun demoAppSwitcher(): DemoAppSwitcher =
        demoAppSwitcher

}

@Composable
@ExperimentalMaterialApi
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Column(modifier = defaultModifier) {
        ChangeThemeFragment(
            themeProperties = settingsViewModel.getThemeProperties(isSystemInDarkTheme()),
        )
        Divider()
        DemoModeFragment(
            demoButtonText = settingsViewModel.demoAppSwitcher().buttonText(),
            switchContextFunction = { settingsViewModel.demoAppSwitcher().switch() }
        )
    }
}