package com.mateuszcholyn.wallet.userConfig.demoMode

import android.content.Context
import androidx.core.content.edit

object DemoModeConfig {
    private const val SHARED_PREFERENCES_NAME = "walletPreferences"
    private const val IS_IN_DEMO_MODE_PREFERENCE_KEY = "isInDemoMode"

    fun setDemoModeFlag(context: Context, value: Boolean) {
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit(commit = true) {
                putBoolean(IS_IN_DEMO_MODE_PREFERENCE_KEY, value)
            }
    }

    fun isDemoModeEnabled(context: Context): Boolean =
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getBoolean(IS_IN_DEMO_MODE_PREFERENCE_KEY, false)
}