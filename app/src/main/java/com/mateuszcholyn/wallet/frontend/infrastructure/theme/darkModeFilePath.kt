package com.mateuszcholyn.wallet.frontend.infrastructure.theme

import android.content.Context
import android.os.Environment
import java.io.File

fun Context.darkModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DARK_MODE.txt"
}
