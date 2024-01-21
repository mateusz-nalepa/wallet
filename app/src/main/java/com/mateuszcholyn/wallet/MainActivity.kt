package com.mateuszcholyn.wallet

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mateuszcholyn.wallet.frontend.view.skeleton.MainScreen
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.util.permissionchecker.verifyStoragePermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val image: Painter = painterResource(id = R.drawable.ic_launcher_foreground)
        Image(painter = image, contentDescription = "App Icon")
    }
}

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("walletSettings")

        private val USER_TOKEN_KEY = stringPreferencesKey("userSelectedTheme")
    }

    val getSelectedTheme: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }

    suspend fun saveSelectedTheme(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)

        installSplashScreen().setKeepOnScreenCondition {
            SelectedColorsMutableState.value == null
        }

        setContent {
            MyApp()
        }
    }

}


fun resolveThemeColors(actualTheme: String?): Colors =
    when (actualTheme) {
        "light" -> lightColors()
        "dark" -> darkColors()
        else -> lightColors()
    }




val SelectedColorsMutableState = mutableStateOf<String?>(null)

val LocalIsLightThemeComposition = compositionLocalOf { SelectedColorsMutableState }
//val LocalIsLightTheme = compositionLocalOf { true } // gdyby wartsc miała być stała


private fun defaultTheme(isSystemInDarkTheme: Boolean) =
    when (isSystemInDarkTheme) {
        true -> "dark"
        false -> "light"
    }

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyApp() {

    var isLoading by rememberSaveable { mutableStateOf(true) }


    val store = UserStore(currentAppContext())

    val isSystemInDarkTheme = isSystemInDarkTheme()


    LaunchedEffect(key1 = "asd") {
        store
            .getSelectedTheme
            .collect { value ->
                SelectedColorsMutableState.value = value ?: defaultTheme(isSystemInDarkTheme)
                isLoading = false
            }

    }


//    if (!isLoading) {
    CompositionLocalProvider(LocalIsLightThemeComposition provides rememberSaveable { SelectedColorsMutableState }) {
        // Teraz możesz użyć LocalIsLightTheme w dowolnym miejscu w Twojej aplikacji

        MaterialTheme(
            colors = resolveThemeColors(LocalIsLightThemeComposition.current.value),
        ) {
            Surface(color = MaterialTheme.colors.background) {
//            Surface {
                MainScreen()
            }
        }
    }
//    } else {
//        LoadingScreen()
//
//
//    }

}