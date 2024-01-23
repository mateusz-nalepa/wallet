package com.mateuszcholyn.wallet.frontend.view.util

import android.content.Context
import android.content.Intent


object AppRestartService {

    fun restart(value: Boolean, context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        // TODO: pass Map<String, Any> as a parameter to restart method
        mainIntent.putExtra("isDemoModeEnabled", value)

        // Required for API 34 and later
        // Ref: https://developer.android.com/about/versions/14/behavior-changes-14#safer-intents
        mainIntent.setPackage(context.packageName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

}

