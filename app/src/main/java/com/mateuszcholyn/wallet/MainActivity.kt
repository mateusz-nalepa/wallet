package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.scaffold.MainScreen
import org.kodein.di.compose.withDI


class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ApplicationContext.appActivity = this

        setContent {
            withDI(di = ApplicationContext.appDi) {
                MaterialTheme {
                    ProvideWindowInsets {
                        val systemUiController = rememberSystemUiController()
                        val darkIcons = MaterialTheme.colors.isLight
                        SideEffect {
                            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
                        }
                        MainScreen()
                    }
                }
            }

        }
    }
}
