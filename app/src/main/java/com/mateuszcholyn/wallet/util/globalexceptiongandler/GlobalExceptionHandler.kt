package com.mateuszcholyn.wallet.util.globalexceptiongandler

import android.content.Context
import android.widget.Toast

class GlobalExceptionHandler(val context: Context) : Thread.UncaughtExceptionHandler {

    private var rootHandler: Thread.UncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, ex: Throwable) {

        ex.printStackTrace()

        Toast
            .makeText(context, ex.message, Toast.LENGTH_LONG)
            .show()
    }
}