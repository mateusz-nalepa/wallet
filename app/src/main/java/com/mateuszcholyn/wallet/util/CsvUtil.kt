package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.mateuszcholyn.wallet.config.ApplicationContext.Companion.appContext
import org.joda.time.LocalDate
import java.io.File

private val walletPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).parent + File.separator + "Wallet"

fun saveToFile(activity: Activity) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    verifyStoragePermissions(activity)

    val file = File(walletFilePath())
    runCatching {
//        file.mkdirs()
        file.createNewFile()
        file.writeText("asd")
    }
            .onFailure { nieDziala(it) }
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

private fun walletFilePath(): String {
    return "$walletPath${File.separator}wallet_${LocalDate.now()}.csv"
}