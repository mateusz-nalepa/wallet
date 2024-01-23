package com.mateuszcholyn.wallet.frontend.view.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.view.screen.settings.demomode.DemoModeFragment
import com.mateuszcholyn.wallet.frontend.view.screen.settings.language.ChangeThemeFragment
import com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown.ChangeLanguageFragment
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val demoAppSwitcher: DemoAppSwitcher,
) : ViewModel() {

    fun demoAppSwitcher(): DemoAppSwitcher =
        demoAppSwitcher

}

@Composable
@ExperimentalMaterialApi
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = currentAppContext()
    Column(modifier = defaultModifier) {
        ChangeThemeFragment()
        ChangeLanguageFragment()
        Divider()
        DemoModeFragment(
            demoButtonText = stringResource(settingsViewModel.demoAppSwitcher().buttonMessageKey()),
            switchContextFunction = { settingsViewModel.demoAppSwitcher().switch(context) }
        )
    }
}
