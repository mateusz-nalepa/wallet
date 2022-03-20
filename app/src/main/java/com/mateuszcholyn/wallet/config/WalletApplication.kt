package com.mateuszcholyn.wallet.config

import android.app.Activity
import android.app.Application
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler


class WalletApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase(this)
        GlobalExceptionHandler(this)
    }

    companion object {
        lateinit var appActivity: Activity
    }
}

