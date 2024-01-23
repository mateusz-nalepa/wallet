package com.mateuszcholyn.wallet.frontend.domain.demomode

import android.content.Context
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.AppRestartService

interface DemoAppSwitcher {

    fun isDemoModeEnabled(): Boolean
    fun switch(context: Context)
    fun buttonMessageKey(): Int

}

class DemoModeEnabled : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        true

    override fun buttonMessageKey(): Int =
        R.string.leaveDemoMode

    override fun switch(context: Context) {
        AppRestartService.restart(false, context)
    }
}


class DemoModeDisabled : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        false

    override fun buttonMessageKey(): Int =
        R.string.startDemoMode

    override fun switch(context: Context) {
        AppRestartService.restart(true, context)
    }
}
