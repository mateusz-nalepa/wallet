package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeDisabledMarker
import com.mateuszcholyn.wallet.frontend.infrastructure.util.createNewIfNotExists
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted


class DemoModeDisabled(
    private val context: Context,
) : DemoModeDisabledMarker {
    override fun switch() {
        enableDemoMode(ctx = context)
        ProcessPhoenix.triggerRebirth(context)
    }
}

private fun enableDemoMode(ctx: Context) {
    if (mediaIsNotMounted()) {
        return
    }
    runCatching { ctx.demoModeFile().createNewIfNotExists() }
        .getOrElse { }
}
