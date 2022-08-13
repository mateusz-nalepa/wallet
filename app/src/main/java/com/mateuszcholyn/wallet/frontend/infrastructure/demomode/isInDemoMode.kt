package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.Context
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted

fun isInDemoMode(ctx: Context): Boolean {
    if (mediaIsNotMounted()) {
        return false
    }
    return runCatching { ctx.demoModeFile().exists() }
        .getOrElse { false }
}
