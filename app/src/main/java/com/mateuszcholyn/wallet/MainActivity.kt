package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.mateuszcholyn.wallet.di.resolveDi
import com.mateuszcholyn.wallet.ui.skeleton.MainScreen
import com.mateuszcholyn.wallet.util.darkmode.resolveTheme
import dagger.hilt.android.AndroidEntryPoint
import org.kodein.di.compose.withDI


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            withDI(di = resolveDi(this)) {
                val themeProperties = resolveTheme(this)
                MaterialTheme(colors = themeProperties.colors) {
                    Surface(color = MaterialTheme.colors.background) {
                        MainScreen(themeProperties = themeProperties)
                    }
                }
            }
        }
    }

}
