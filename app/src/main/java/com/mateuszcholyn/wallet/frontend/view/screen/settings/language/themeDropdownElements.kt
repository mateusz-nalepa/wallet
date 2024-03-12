package com.mateuszcholyn.wallet.frontend.view.screen.settings.language

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement
import com.mateuszcholyn.wallet.userConfig.theme.WalletTheme

data class ThemeDropdownElement(
    override val name: String? = null,
    override val subName: String? = null,
    override val nameKey: Int,
    val walletTheme: WalletTheme,
) : DropdownElement

@Composable
fun themeDropdownElements(): List<ThemeDropdownElement> =
    listOf(
        ThemeDropdownElement(
            nameKey = R.string.settings_theme_useSystemTheme,
            walletTheme = WalletTheme.SYSTEM,
        ),
        ThemeDropdownElement(
            nameKey = R.string.settings_theme_lightTheme,
            walletTheme = WalletTheme.LIGHT,
        ),
        ThemeDropdownElement(
            nameKey = R.string.settings_theme_darkTheme,
            walletTheme = WalletTheme.DARK,
        )
    )
