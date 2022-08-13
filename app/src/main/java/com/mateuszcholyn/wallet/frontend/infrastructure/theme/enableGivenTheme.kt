package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import android.content.Context
import com.mateuszcholyn.wallet.frontend.domain.theme.Resolver
import com.mateuszcholyn.wallet.frontend.infrastructure.util.createNewIfNotExists
import com.mateuszcholyn.wallet.frontend.infrastructure.util.mediaIsNotMounted
import com.mateuszcholyn.wallet.frontend.infrastructure.util.toFile

fun enableGivenTheme(
    ctx: Context,
    resolver: Resolver,
) {
    if (mediaIsNotMounted()) {
        return
    }

    try {
        ctx
            .darkModeFilePath()
            .toFile()
            .delete()
        if (Resolver.SYSTEM == resolver) {
            // we don't want to create file in this case
            return
        }

        ctx
            .darkModeFilePath()
            .toFile()
            .createNewIfNotExists()
            .writeText(resolver.name)

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}
