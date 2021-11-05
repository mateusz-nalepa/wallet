package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.mateuszcholyn.wallet.config.ApplicationContext.Companion.appContext
import com.mateuszcholyn.wallet.domain.expense.Expense
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime

private val walletPath =
    Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).parent + File.separator + "Wallet"

fun saveToFile(activity: Activity, expens: List<Expense>) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    verifyStoragePermissions(activity)

    val file = File(walletFilePath())
    val fileWriter = FileWriter(file)

    runCatching {
        file.createNewFile()
        fileWriter.write(prepareHeader())
        expens.forEach { fileWriter.write(prepareLine(it)) }
        fileWriter.close()
    }
        .onFailure { nieDziala(it) }
        .onSuccess { dziala() }
}


private fun nieDziala(ex: Throwable) {
    Toast.makeText(
        appContext, "Błąd zapisu: ${ex.message}", LENGTH_LONG
    ).show()
}

private fun dziala() {
    Toast.makeText(
        appContext, "Pomyślnie zapisano", LENGTH_LONG
    ).show()
}

private fun walletFilePath(): String {
    return "$walletPath${File.separator}wallet_${LocalDateTime.now()}.txt"
}

private fun prepareLine(ex: Expense) =
    "${ex.id},${ex.amount},${ex.category.name},${ex.date},${ex.description}\n"


private fun prepareHeader() =
    "Id,Kwota,Kategoria,Data,Opis\n"
