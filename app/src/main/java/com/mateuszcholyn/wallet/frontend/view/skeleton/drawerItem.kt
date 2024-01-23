package com.mateuszcholyn.wallet.frontend.view.skeleton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.userConfig.theme.LocalWalletThemeComposition

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
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = stringResource(id = item.titleTranslationKey),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = item.titleTranslationKey),
            fontSize = 18.sp,
//            color = MaterialTheme.colors.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LightDrawerItemPreviewSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf("light") }) {
        MaterialTheme(colors = lightColors()) {
            DrawerItem(item = NavDrawerItem.Category, selected = true, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightDrawerItemPreviewNotSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf("light") }) {
        MaterialTheme(colors = lightColors()) {
            DrawerItem(item = NavDrawerItem.Category, selected = false, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkDrawerItemPreviewSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf("dark") }) {
        MaterialTheme(colors = darkColors()) {
            DrawerItem(item = NavDrawerItem.Category, selected = true, onItemClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkDrawerItemPreviewNotSelectedWithBackground() {
    CompositionLocalProvider(LocalWalletThemeComposition provides remember { mutableStateOf("dark") }) {
        MaterialTheme(colors = darkColors()) {
            DrawerItem(item = NavDrawerItem.Category, selected = false, onItemClick = {})
        }
    }
}
