package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import java.io.File


@Suppress("unused")
fun enableDemoMode(ctx: Context, activity: Activity): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    verifyStoragePermissions(activity)

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
fun disableDemoMode(ctx: Context, activity: Activity): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    verifyStoragePermissions(activity)

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
fun isInDemoMode(ctx: Context, activity: Activity): Boolean {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return false
    }

    verifyStoragePermissions(activity)

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