package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mateuszcholyn.wallet.frontend.view.skeleton.MainScreen
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.util.permissionchecker.verifyStoragePermissions
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)

        setContent {
            MyApp()
        }
    }

}


val SelectedColorsMutableState = mutableStateOf(true)

val LocalIsLightThemeComposition = compositionLocalOf { SelectedColorsMutableState }
//val LocalIsLightTheme = compositionLocalOf { true } // gdyby wartsc miała być stała

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyApp() {
    // Dostarcz wartość dla LocalIsLightTheme
    val xd = isSystemInDarkTheme()
    CompositionLocalProvider(LocalIsLightThemeComposition provides remember { SelectedColorsMutableState }) {
        // Teraz możesz użyć LocalIsLightTheme w dowolnym miejscu w Twojej aplikacji


        MaterialTheme(
            colors = if (LocalIsLightThemeComposition.current.value) lightColors() else darkColors(),
        ) {
            Surface(color = MaterialTheme.colors.background) {
                MainScreen()
            }
        }
    }
}