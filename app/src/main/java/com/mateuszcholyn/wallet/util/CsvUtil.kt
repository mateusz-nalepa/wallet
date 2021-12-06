package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.mateuszcholyn.wallet.config.ApplicationContext.Companion.appContext
import com.mateuszcholyn.wallet.domain.expense.Expense
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime

@Suppress("unused")
fun saveToFile(ctx: Context, activity: Activity, expens: List<Expense>) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    verifyStoragePermissions(activity)

    try {
        ctx
            .walletFilePath()
            .toFile()
            .createNewIfNotExists()
            .toFileWriter()
            .use { fileWriter ->
                fileWriter.write(prepareHeader())
                expens.forEach { fileWriter.write(prepareLine(it)) }
                fileSaved()
            }
    } catch (t: Throwable) {
        fileNotSaved(t)
    }
}


private fun fileNotSaved(ex: Throwable) {
    Toast.makeText(
        appContext, "Błąd zapisu: ${ex.message}", LENGTH_LONG
    ).show()
}

private fun fileSaved() {
    Toast.makeText(
        appContext, "Pomyślnie zapisano", LENGTH_LONG
    ).show()
}

private fun Context.walletFilePath(): String {
    val appPath = this.getExternalFilesDir(DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}wallet_${LocalDateTime.now().toHumanText()}.txt"
}

private fun String.toFile(): File {
    return File(this)
}

private fun prepareHeader() =
    "Id,Kwota,Kategoria,Data,Opis\n"


private fun prepareLine(ex: Expense): String =
    "${ex.id},${ex.amount},${ex.category.name},${ex.date.toHumanText()},${ex.description}\n"


private fun File.createNewIfNotExists(): File {
    if (!this.exists()) {
        this.createNewFile()
    }

    return this
}

private fun File.toFileWriter(): FileWriter {
    return FileWriter(this)
}