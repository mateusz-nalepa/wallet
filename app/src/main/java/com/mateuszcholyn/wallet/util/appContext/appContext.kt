package com.mateuszcholyn.wallet.util.appContext

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun currentAppContext(): Context {
    return LocalContext.current
}
