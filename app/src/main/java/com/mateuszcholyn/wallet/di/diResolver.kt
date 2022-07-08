package com.mateuszcholyn.wallet.di

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.di.appdi.createDependencyContext
import com.mateuszcholyn.wallet.di.demodi.simpleDi
import com.mateuszcholyn.wallet.util.appContext.currentAppContext
import com.mateuszcholyn.wallet.util.demomode.isInDemoMode
import org.kodein.di.DI

@Composable
fun resolveDi(): DI {
    val isDemoMode = isInDemoMode(ctx = currentAppContext())

    return if (isDemoMode) {
        simpleDi {}
    } else {
        createDependencyContext(currentAppContext())
    }
}