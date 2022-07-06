package com.mateuszcholyn.wallet.config

import android.app.Application
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler
import dagger.hilt.android.HiltAndroidApp


@Suppress("unused")
/**
 * This file is used in ApplicationManifest.xml
 */
@HiltAndroidApp
class WalletApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalExceptionHandler(this)
    }

}
