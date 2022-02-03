package com.mateuszcholyn.wallet.domain

import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.util.disableDemoMode
import com.mateuszcholyn.wallet.util.enableDemoMode

interface DemoAppEnabledProvider {

    fun isDemoModeEnabled(): Boolean
    fun changeContext()
    fun buttonText(): String

}

object DemoModeEnabled : DemoAppEnabledProvider {
    override fun isDemoModeEnabled(): Boolean {
        return true
    }

    override fun changeContext() {
        disableDemoMode(
                ctx = ApplicationContext.appContext,
                activity = ApplicationContext.appActivity,
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

    override fun changeContext() {
        enableDemoMode(
                ctx = ApplicationContext.appContext,
                activity = ApplicationContext.appActivity,
        )
    }

    override fun buttonText(): String {
        return "Uruchom tryb demo"
    }
}