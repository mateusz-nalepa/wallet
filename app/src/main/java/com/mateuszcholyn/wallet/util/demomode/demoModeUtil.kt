package com.mateuszcholyn.wallet.util.demomode

import android.content.Context
import android.os.Environment
import com.mateuszcholyn.wallet.util.createNewIfNotExists
import com.mateuszcholyn.wallet.util.mediaIsNotMounted
import com.mateuszcholyn.wallet.util.toFile
import java.io.File


fun enableDemoMode(ctx: Context) {
    if (mediaIsNotMounted()) {
        return
    }
    runCatching { ctx.demoModeFile().createNewIfNotExists() }
            .getOrElse { }
}

fun disableDemoMode(ctx: Context) {
    if (mediaIsNotMounted()) {
        return
    }
    runCatching { ctx.demoModeFile().delete() }
            .getOrElse { }
}

fun isInDemoMode(ctx: Context): Boolean {
    if (mediaIsNotMounted()) {
        return false
    }
    return runCatching { ctx.demoModeFile().exists() }
            .getOrElse { false }
}

private fun Context.demoModeFile(): File =
        this
                .demoModeFilePath()
                .toFile()

private fun Context.demoModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DEMO_MODE_ENABLED.txt"
}