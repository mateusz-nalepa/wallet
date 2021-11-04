package com.mateuszcholyn.wallet.util

import android.content.Context
import android.widget.Toast
import com.mateuszcholyn.wallet.config.ApplicationContext

class GlobalExceptionHandler(val context: Context) : Thread.UncaughtExceptionHandler {

    private var rootHandler: Thread.UncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, ex: Throwable) {
        Toast
            .makeText(ApplicationContext.appContext, ex.message, Toast.LENGTH_LONG)
            .show()
    }
}