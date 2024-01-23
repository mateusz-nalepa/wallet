package com.mateuszcholyn.wallet.frontend.domain.demomode

import android.content.Context
import com.mateuszcholyn.wallet.frontend.view.util.AppRestartService

interface DemoAppSwitcher {

    fun isDemoModeEnabled(): Boolean
    fun switch(context: Context)
    fun buttonText(): String

}

class DemoModeEnabled : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        true

    override fun buttonText(): String =
        "Wyjdź z trybu demo"

    override fun switch(context: Context) {
        AppRestartService.restart(false, context)
    }
}


class DemoModeDisabled : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        false

    override fun buttonText(): String =
        "Uruchom tryb demo"

    override fun switch(context: Context) {
        AppRestartService.restart(true, context)
    }
}
