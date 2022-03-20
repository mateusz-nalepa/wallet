package com.mateuszcholyn.wallet.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.ui.screen.settings.demomode.DemoModeFragment
import com.mateuszcholyn.wallet.ui.screen.settings.themedropdown.ChangeThemeFragment
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.ThemeProperties


@Composable
@ExperimentalMaterialApi
fun SettingsScreen(themeProperties: ThemeProperties) {

    Column(modifier = defaultModifier) {
        ChangeThemeFragment(themeProperties)
        Divider()
        DemoModeFragment()
    }
}
