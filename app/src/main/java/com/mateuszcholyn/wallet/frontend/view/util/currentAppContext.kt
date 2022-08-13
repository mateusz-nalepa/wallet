package com.mateuszcholyn.wallet.frontend.view.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun currentAppContext(): Context {
    return LocalContext.current
}
