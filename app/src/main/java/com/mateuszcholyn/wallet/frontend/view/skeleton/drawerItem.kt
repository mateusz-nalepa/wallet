package com.mateuszcholyn.wallet.frontend.view.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.userConfig.theme.LocalWalletThemeComposition
import com.mateuszcholyn.wallet.userConfig.theme.WalletTheme

private fun Modifier.backgroundIfSelected(selected: Boolean): Modifier =
    composed {
        if (selected) {
            background(MaterialTheme.colors.primarySurface)
        } else {
            this
        }
    }

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .backgroundIfSelected(selected)
            .padding(start = 10.dp)
    ) {
        Icon(
            imageVector = item.imageVector,
            // FIXME: add description for accessibility
            contentDescription = "",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp),
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = item.titleTranslationKey),
            fontSize = 18.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LightDrawerItemPreviewSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.LIGHT.themeName) }) {
        MaterialTheme(colors = lightColors()) {
            DrawerItem(item = NavDrawerItem.Categories, selected = true, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightDrawerItemPreviewNotSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.LIGHT.themeName) }) {
        MaterialTheme(colors = lightColors()) {
            DrawerItem(item = NavDrawerItem.Categories, selected = false, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkDrawerItemPreviewSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.DARK.themeName) }) {
        MaterialTheme(colors = darkColors()) {
            DrawerItem(item = NavDrawerItem.Categories, selected = true, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkDrawerItemPreviewNotSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf(WalletTheme.DARK.themeName) }) {
        MaterialTheme(colors = darkColors()) {
            DrawerItem(item = NavDrawerItem.Categories, selected = false, onItemClick = {})
        }
    }
}
