package com.mateuszcholyn.wallet.domain

import android.app.Activity
import android.content.Context
import com.mateuszcholyn.wallet.util.disableDemoMode
import com.mateuszcholyn.wallet.util.enableDemoMode

interface DemoAppEnabledProvider {

    fun isDemoModeEnabled(): Boolean
    fun changeContext(context: Context, activity: Activity)
    fun buttonText(): String

}

object DemoModeEnabled : DemoAppEnabledProvider {
    override fun isDemoModeEnabled(): Boolean {
        return true
    }

    override fun changeContext(context: Context, activity: Activity) {
        disableDemoMode(
                ctx = context,
                activity = activity,
        )
    }

    override fun buttonText(): String {
        return "Wyjdź z trybu demo"
    }
}

object DemoModeDisabled : DemoAppEnabledProvider {
    override fun isDemoModeEnabled(): Boolean {
        return false
    }

    override fun changeContext(context: Context, activity: Activity) {
        enableDemoMode(
                ctx = context,
                activity = activity,
        )
    }

    override fun buttonText(): String {
        return "Uruchom tryb demo"
    }
}