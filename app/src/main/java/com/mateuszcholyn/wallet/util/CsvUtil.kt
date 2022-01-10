package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.config.ApplicationContext.Companion.appContext
import com.mateuszcholyn.wallet.domain.expense.Expense
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime


@Suppress("unused")
fun saveToFile(ctx: Context, activity: Activity, expenses: List<Expense>) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    verifyStoragePermissions(activity)


    val objectMapper = ObjectMapper().findAndRegisterModules()

    try {
        ctx
                .walletFilePath()
                .toFile()
                .createNewIfNotExists()
                .also { println("File location is: ${it.absolutePath}") }
                .toFileWriter()
                .use { fileWriter ->
                    val expensesAsJson =
                            expenses
                                    .map { prepareSaveModel(it) }
                                    .let { objectMapper.writeValueAsString(it) }

                    fileWriter.write(expensesAsJson)
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
    return "$appPath${File.separator}wallet_${LocalDateTime.now().toHumanText()}.json"
}

private fun String.toFile(): File {
    return File(this)
}



private fun prepareSaveModel(ex: Expense): SaveModel =
        SaveModel(
                expenseId = ex.id,
                amount = ex.amount,
                categoryName = ex.category.name,
                date = ex.date.toHumanText(),
                description = ex.description,
        )

private fun File.createNewIfNotExists(): File {

    if (!this.exists()) {
        this.createNewFile()
    }

    return this
}

private fun File.toFileWriter(): FileWriter {
    return FileWriter(this)
}

data class SaveModel(
        val expenseId: Long,
        val amount: Double,
        val categoryName: String,
        val date: String,
        val description: String,
)