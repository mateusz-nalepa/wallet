package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement
import com.mateuszcholyn.wallet.userConfig.theme.WalletTheme

data class ThemeDropdownElement(
    override val name: String,
    override val nameKey: Int? = null,
    val walletTheme: WalletTheme,
) : DropdownElement

@Composable
fun themeDropdownElements(): List<ThemeDropdownElement> =
    listOf(
        ThemeDropdownElement(
            name = stringResource(R.string.useSystemTheme),
            walletTheme = WalletTheme.SYSTEM,
        ),
        ThemeDropdownElement(
            name = stringResource(R.string.lightTheme),
            walletTheme = WalletTheme.LIGHT,
        ),
        ThemeDropdownElement(
            name = stringResource(R.string.darkTheme),
            walletTheme = WalletTheme.DARK,
        )
    )
