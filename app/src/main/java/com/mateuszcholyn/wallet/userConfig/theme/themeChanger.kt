package com.mateuszcholyn.wallet.userConfig.theme

import com.mateuszcholyn.wallet.userConfig.UserConfigProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ThemeChanger {
    fun enableGivenTheme(
        userConfigProvider: UserConfigProvider,
        themeName: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            WalletThemeSelectedByUser.value = themeName
            userConfigProvider.saveSelectedTheme(themeName)
        }
    }
}
