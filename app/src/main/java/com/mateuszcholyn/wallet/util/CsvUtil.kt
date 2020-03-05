package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.os.Environment
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.mateuszcholyn.wallet.config.ApplicationContext.Companion.appContext
import java.io.File

fun saveToFile(activity: Activity) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "dane.txt")

    verifyStoragePermissions(activity)
    runCatching {
        file.createNewFile()
        file.writeText("asd")
    }.onFailure { nieDziala(it) }
            .onSuccess { dziala() }

}


private fun nieDziala(ex: Throwable) {
    Toast.makeText(
            appContext, "Nie dziala: ${ex.message}", LENGTH_LONG).show()
}

private fun dziala() {
    Toast.makeText(
            appContext, "Dziala", LENGTH_LONG).show()
}