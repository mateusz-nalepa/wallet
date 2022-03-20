package com.mateuszcholyn.wallet.di

import android.app.Activity

class ActivityProvider(
        private val activity: Activity,
) {

    fun get(): Activity {
        return activity
    }

}