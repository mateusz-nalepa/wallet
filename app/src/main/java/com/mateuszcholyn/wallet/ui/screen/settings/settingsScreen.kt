package com.mateuszcholyn.wallet.ui.screen.settings

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.ui.screen.settings.demomode.DemoModeFragment
import com.mateuszcholyn.wallet.ui.screen.settings.themedropdown.ChangeThemeFragment
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.darkmode.ThemeProperties
import com.mateuszcholyn.wallet.util.darkmode.resolveTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Composable
@ExperimentalMaterialApi
fun SettingsScreen(
        settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    Column(modifier = defaultModifier) {
        ChangeThemeFragment(settingsViewModel.getThemeProperties(isSystemInDarkTheme()))
        Divider()
        DemoModeFragment()
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
        private val themePropertiesProvider: ThemePropertiesProvider,
) : ViewModel() {

    fun getThemeProperties(isSystemInDarkTheme: Boolean) =
            themePropertiesProvider.provide(isSystemInDarkTheme)

}

interface ThemePropertiesProvider {
    fun provide(isSystemInDarkTheme: Boolean): ThemeProperties
}

class DefaultThemePropertiesProvider(
        private val context: Context,
) : ThemePropertiesProvider {
    override fun provide(isSystemInDarkTheme: Boolean): ThemeProperties {
        return resolveTheme(context, isSystemInDarkTheme)
    }
}
