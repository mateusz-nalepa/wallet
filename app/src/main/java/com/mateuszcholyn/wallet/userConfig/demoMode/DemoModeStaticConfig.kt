package com.mateuszcholyn.wallet.userConfig.demoMode

import android.content.Intent
import kotlin.properties.Delegates

/*
  This class is used only in:
  -> MainActivity to invoke setApplicationDemoModeFlagFrom
  -> DemoAppSwitcherModule to invoke isDemoModeEnabled
  In other places you should use: DemoAppSwitcher bean
 */
object DemoModeStaticConfig {
    private var isDemoModeEnabledFlag by Delegates.notNull<Boolean>()

    fun setApplicationDemoModeFlagFrom(intent: Intent) {
        isDemoModeEnabledFlag = intent.getBooleanExtra("isDemoModeEnabled", false)
    }

    fun isDemoModeEnabled(): Boolean =
        isDemoModeEnabledFlag
}