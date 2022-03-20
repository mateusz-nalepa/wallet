package com.mateuszcholyn.wallet.config

import android.app.Application
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler


@Suppress("unused")
/**
 * This file is used in ApplicationManifest.xml
 */
class WalletApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalExceptionHandler(this)
    }

}
