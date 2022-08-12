package com.mateuszcholyn.wallet.util

import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import com.fasterxml.jackson.databind.ObjectMapper
import com.mateuszcholyn.wallet.ui.screen.dummy.SaveAllExpensesV2WithCategoriesV2Model
import com.mateuszcholyn.wallet.ui.util.showLongText
import com.mateuszcholyn.wallet.util.dateutils.toHumanText
import java.io.File
import java.io.FileWriter
import java.math.BigDecimal
import java.time.LocalDateTime


@Suppress("unused")
fun saveAllExpensesToFile(
    ctx: Context,
    saveAllExpensesV2WithCategoriesV2Model: SaveAllExpensesV2WithCategoriesV2Model
) {
    if (mediaIsNotMounted()) {
        return
    }

    val objectMapper = ObjectMapper().findAndRegisterModules().writerWithDefaultPrettyPrinter()

    try {
        ctx
            .walletFilePath()
            .toFile()
            .createNewIfNotExists()
            .also { println("File location is: ${it.absolutePath}") }
            .toFileWriter()
            .use { fileWriter ->
                val allExpensesV2WithCategoriesV2AsJson =
                    objectMapper.writeValueAsString(saveAllExpensesV2WithCategoriesV2Model)

                fileWriter.write(allExpensesV2WithCategoriesV2AsJson)
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

fun File.createNewIfNotExists(): File {

    if (!this.exists()) {
        this.createNewFile()
    }

    return this
}

private fun File.toFileWriter(): FileWriter {
    return FileWriter(this)
}

