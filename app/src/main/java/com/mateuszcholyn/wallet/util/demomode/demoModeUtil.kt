package com.mateuszcholyn.wallet.util.demomode

import android.content.Context
import android.os.Environment
import com.mateuszcholyn.wallet.util.createNewIfNotExists
import com.mateuszcholyn.wallet.util.toFile
import java.io.File


@Suppress("unused")
fun enableDemoMode(ctx: Context): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    return try {
        ctx
                .demoModeFilePath()
                .toFile()
                .createNewIfNotExists()

        true
    } catch (t: Throwable) {
        false
    }
}

@Suppress("unused")
fun disableDemoMode(ctx: Context): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    return try {
        ctx
                .demoModeFilePath()
                .toFile()
                .delete()

        true
    } catch (t: Throwable) {
        false
    }
}

@Suppress("unused")
fun isInDemoMode(ctx: Context): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    return try {
        ctx
                .demoModeFilePath()
                .toFile()
                .exists()

    } catch (t: Throwable) {
        false
    }
}

private fun Context.demoModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DEMO_MODE_ENABLED.txt"
}