package com.mateuszcholyn.wallet.ui.screen.settings.themedropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.dropdown.DropdownElement
import com.mateuszcholyn.wallet.util.Resolver

data class ThemeDropdownElement(
        override val name: String,
        val resolver: Resolver,
) : DropdownElement

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