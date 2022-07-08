package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.mateuszcholyn.wallet.ui.skeleton.MainScreen
import com.mateuszcholyn.wallet.util.darkmode.resolveTheme
import com.mateuszcholyn.wallet.util.verifyStoragePermissions
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)

        setContent {
            val themeProperties = resolveTheme(this, isSystemInDarkTheme())
            MaterialTheme(colors = themeProperties.colors) {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }

}
