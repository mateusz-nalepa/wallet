package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.ui.skeleton.MainScreen
import com.mateuszcholyn.wallet.util.isInDemoMode
import com.mateuszcholyn.wallet.util.simpleDi
import org.kodein.di.DI
import org.kodein.di.compose.withDI


@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ApplicationContext.appActivity = this

        setContent {
            withDI(di = resolveDi()) {
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

    private fun resolveDi(): DI {
        val isDemoMode =
                isInDemoMode(
                        ctx = ApplicationContext.appContext,
                        activity = this,
                )

        return if (isDemoMode) {
            simpleDi {}
        } else {
            ApplicationContext.appDi
        }
    }
}
