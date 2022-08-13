package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.Context
import android.os.Environment
import com.mateuszcholyn.wallet.frontend.infrastructure.util.toFile
import java.io.File


fun Context.demoModeFile(): File =
    this
        .demoModeFilePath()
        .toFile()

private fun Context.demoModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DEMO_MODE_ENABLED.txt"
}
