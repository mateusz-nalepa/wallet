package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeEnabledMarker
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted

class DemoModeEnabled(
    private val context: Context,
) : DemoModeEnabledMarker {
    override fun switch() {
        disableDemoMode(ctx = context)
        AppRestartService.restart(context)
    }
}

private fun disableDemoMode(ctx: Context) {
    if (mediaIsNotMounted()) {
        return
    }
    runCatching { ctx.demoModeFile().delete() }
        .getOrElse { }
}
