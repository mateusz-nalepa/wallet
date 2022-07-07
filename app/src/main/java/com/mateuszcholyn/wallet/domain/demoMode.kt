package com.mateuszcholyn.wallet.domain

import android.content.Context
import com.mateuszcholyn.wallet.util.demomode.disableDemoMode
import com.mateuszcholyn.wallet.util.demomode.enableDemoMode

interface DemoAppEnabledProvider {

    fun isDemoModeEnabled(): Boolean
    fun changeContext(context: Context)
    fun buttonText(): String

}

object DemoModeEnabled : DemoAppEnabledProvider {
    override fun isDemoModeEnabled(): Boolean {
        return true
    }

    override fun changeContext(context: Context) {
        disableDemoMode(
                ctx = context,
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

    override fun changeContext(context: Context) {
        enableDemoMode(
                ctx = context,
        )
    }

    override fun buttonText(): String {
        return "Uruchom tryb demo"
    }
}