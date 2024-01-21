package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeDisabledMarker
import com.mateuszcholyn.wallet.frontend.infrastructure.util.createNewIfNotExists
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted


class DemoModeDisabled(
    private val context: Context,
) : DemoModeDisabledMarker {
    override fun switch() {
        enableDemoMode(ctx = context)
        AppRestartService.restart(context)
    }
}

private fun enableDemoMode(ctx: Context) {
    if (mediaIsNotMounted()) {
        return
    }
    runCatching { ctx.demoModeFile().createNewIfNotExists() }
        .getOrElse { }
}
