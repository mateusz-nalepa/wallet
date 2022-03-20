package com.mateuszcholyn.wallet.di

import android.app.Activity
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.currentAppContext
import com.mateuszcholyn.wallet.di.appdi.createDependencyContext
import com.mateuszcholyn.wallet.di.demodi.simpleDi
import com.mateuszcholyn.wallet.util.isInDemoMode
import org.kodein.di.DI

@Composable
fun resolveDi(activity: Activity): DI {
    val isDemoMode =
            isInDemoMode(
                    ctx = currentAppContext(),
                    activity = activity,
            )

    return if (isDemoMode) {
        simpleDi {}
    } else {
        createDependencyContext(currentAppContext())
    }
}