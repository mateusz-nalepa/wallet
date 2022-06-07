package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.ui.util.showLongText
import com.mateuszcholyn.wallet.util.dateutils.toHumanText
import java.io.File
import java.io.FileWriter
import java.math.BigDecimal
import java.time.LocalDateTime


@Suppress("unused")
fun saveAllExpensesToFile(ctx: Context, activity: Activity, expenses: List<Expense>) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }
    verifyStoragePermissions(activity)


    val objectMapper = ObjectMapper().findAndRegisterModules().writerWithDefaultPrettyPrinter()

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
                    fileSaved(ctx)
                }
    } catch (t: Throwable) {
        fileNotSaved(ctx, t)
    }
}


private fun fileNotSaved(appContext: Context, ex: Throwable) {
    showLongText(appContext, "Błąd zapisu: ${ex.message}")
}

private fun fileSaved(appContext: Context) {
    showLongText(appContext, "Pomyślnie zapisano")
}

private fun Context.walletFilePath(): String {
    val appPath = this.getExternalFilesDir(DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}wallet_${LocalDateTime.now().toHumanText()}.json"
}

fun String.toFile(): File {
    return File(this)
}


private fun prepareSaveModel(ex: Expense): SaveModel =
        SaveModel(
                expenseId = ex.idOrThrow(),
                amount = ex.amount,
                categoryId = ex.category.id,
                categoryName = ex.category.name,
                date = ex.date.toHumanText(),
                description = ex.description,
        )

fun File.createNewIfNotExists(): File {

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
        val amount: BigDecimal,
        val categoryId: Long,
        val categoryName: String,
        val date: String,
        val description: String,
)