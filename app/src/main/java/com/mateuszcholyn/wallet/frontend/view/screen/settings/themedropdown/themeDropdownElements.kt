package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.theme.Resolver
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement

data class ThemeDropdownElement(
    override val name: String,
    override val nameKey: Int? = null,
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
        )
    )
}