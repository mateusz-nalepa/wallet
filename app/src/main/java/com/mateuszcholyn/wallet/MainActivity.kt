package com.mateuszcholyn.wallet

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mateuszcholyn.wallet.di.resolveDi
import com.mateuszcholyn.wallet.ui.skeleton.MainScreen
import com.mateuszcholyn.wallet.util.darkmode.ThemeProperties
import com.mateuszcholyn.wallet.util.darkmode.resolveTheme
import org.kodein.di.compose.withDI


@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            withDI(di = resolveDi(this)) {

                val themeProperties = resolveTheme()

                MaterialTheme(colors = themeProperties.colors) {
                    ProvideWindowInsets {
                        val systemUiController = rememberSystemUiController()
                        SideEffect {
                            systemUiController.setSystemBarsColor(
                                    color = Color.Transparent,
                                    darkIcons = themeProperties.shouldUseDarkTheme,
                            )
                        }
                        MainScreen(themeProperties = themeProperties)
                    }
                }
            }
        }
    }


    @Composable
    private fun resolveTheme(): ThemeProperties =
            resolveTheme(
                    ctx = currentAppContext(),
                    activity = this,
            )
}


@Composable
fun currentAppContext(): Context {
    return LocalContext.current
}
