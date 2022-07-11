package com.mateuszcholyn.wallet.domain

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.util.demomode.disableDemoMode
import com.mateuszcholyn.wallet.util.demomode.enableDemoMode

interface DemoAppSwitcher {

    fun isDemoModeEnabled(): Boolean
    fun switch()
    fun buttonText(): String

}

class DemoModeEnabled(
    private val context: Context,
) : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean = true

    override fun switch() {
        disableDemoMode(ctx = context)
        ProcessPhoenix.triggerRebirth(context)
    }

    override fun buttonText(): String {
        return "Wyjdź z trybu demo"
    }
}

class DemoModeDisabled(
    private val context: Context,
) : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean = false

    override fun switch() {
        enableDemoMode(ctx = context)
        ProcessPhoenix.triggerRebirth(context)
    }

    override fun buttonText(): String {
        return "Uruchom tryb demo"
    }
}