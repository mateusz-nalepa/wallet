package com.mateuszcholyn.wallet

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mateuszcholyn.wallet.frontend.view.skeleton.MainScreen
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.util.permissionchecker.verifyStoragePermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "ładowanie XD", color = MaterialTheme.colors.primary)
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

        setContent {
            MyApp()
        }
    }

}

fun ustawKolorkiXD(isSystemInDarkTheme: Boolean): String =
    if (isSystemInDarkTheme) {
        "dark"
    } else {
        "light"
    }


private fun resolveThemeV2(actualTheme: String?): Colors =
    when (actualTheme) {
        "light" -> lightColors()
        "dark" -> darkColors()
        else -> lightColors()
    }


val SelectedColorsMutableState = mutableStateOf<String?>(null)

val LocalIsLightThemeComposition = compositionLocalOf { SelectedColorsMutableState }
//val LocalIsLightTheme = compositionLocalOf { true } // gdyby wartsc miała być stała

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyApp() {

    var isLoading by rememberSaveable { mutableStateOf(true) }


    val store = UserStore(currentAppContext())


    LaunchedEffect(key1 = "asd") {
        CoroutineScope(Dispatchers.IO).launch {
            store
                .getSelectedTheme
                .collect { value ->
                    SelectedColorsMutableState.value = value
                    isLoading = false
                }
        }
    }


    if (isLoading) {
        LoadingScreen()
    } else {
        CompositionLocalProvider(LocalIsLightThemeComposition provides rememberSaveable { SelectedColorsMutableState }) {
            // Teraz możesz użyć LocalIsLightTheme w dowolnym miejscu w Twojej aplikacji

            MaterialTheme(
                colors = resolveThemeV2(LocalIsLightThemeComposition.current.value),
            ) {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }

    }

}