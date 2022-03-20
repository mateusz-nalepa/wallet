package com.mateuszcholyn.wallet.ui.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.Resolver

@Composable
fun themeDropdownElements(): List<ThemeDropdownElement> {
    return listOf(
            ThemeDropdownElement(
                    name = stringResource(R.string.useSystemTheme),
                    resolver = Resolver.SYSTEM,
            ),
            ThemeDropdownElement(
                    name = stringResource(R.string.lightTheme),
                    resolver = Resolver.LIGHT,

                    ),
            ThemeDropdownElement(
                    name = stringResource(R.string.darkTheme),
                    resolver = Resolver.DARK,
            ))
}