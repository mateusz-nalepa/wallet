package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mateuszcholyn.wallet.frontend.view.skeleton.MainScreen
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.userConfig.UserConfigProvider
import com.mateuszcholyn.wallet.userConfig.theme.LocalWalletThemeComposition
import com.mateuszcholyn.wallet.userConfig.theme.WalletThemeSelectedByUser
import com.mateuszcholyn.wallet.userConfig.theme.resolveThemeColors
import com.mateuszcholyn.wallet.userConfig.theme.resolveThemeName
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: przenieś to w inne miejsce XD

        installSplashScreen().setKeepOnScreenCondition {
            WalletThemeSelectedByUser.value == null
        }

        setContent {
            MyApp()
        }
    }

}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyApp() {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    InitializeAppBasedOnUserPreferences(isSystemInDarkTheme)
    CompositionLocalProvider(LocalWalletThemeComposition provides rememberSaveable { WalletThemeSelectedByUser }) {
        MaterialTheme(
            colors = resolveThemeColors(
                LocalWalletThemeComposition.current.value,
                isSystemInDarkTheme,
            )
        ) {
            Surface(color = MaterialTheme.colors.background) {
                MainScreen()
            }
        }
    }
}


@Composable
fun InitializeAppBasedOnUserPreferences(isSystemInDarkTheme: Boolean) {
    val userConfigProvider = UserConfigProvider(currentAppContext())
    LaunchedEffect(key1 = "whateverXD") {
        userConfigProvider
            .getUserDataStoreConfig()
            .collect { userConfig ->
                WalletThemeSelectedByUser.value =
                    resolveThemeName(userConfig?.selectedTheme, isSystemInDarkTheme)
            }
    }
}
