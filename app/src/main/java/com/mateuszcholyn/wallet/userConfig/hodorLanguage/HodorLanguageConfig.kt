package com.mateuszcholyn.wallet.userConfig.hodorLanguage

import android.content.Context
import androidx.core.content.edit

const val numberOfClicksToHaveHodorLanguage = 10

object HodorLanguageConfig {
    private const val SHARED_PREFERENCES_NAME = "walletPreferences"
    private const val IS_HODOR_LANGUAGE_AVAILABLE_KEY = "isHodorLanguageAvailable"

    fun setHodorLanguageToAvailable(context: Context) {
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit(commit = true) {
                putBoolean(IS_HODOR_LANGUAGE_AVAILABLE_KEY, false)
            }
    }

    fun isHodorLanguageNotAvailable(context: Context): Boolean =
        context
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getBoolean(IS_HODOR_LANGUAGE_AVAILABLE_KEY, true)
}