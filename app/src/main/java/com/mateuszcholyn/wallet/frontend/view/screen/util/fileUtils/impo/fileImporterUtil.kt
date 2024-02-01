package com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo

import android.content.Context
import android.net.Uri
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.clearDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun File.readFileContent(): String =
    bufferedReader()
        .readText()

fun Context.externalFileToInternal(
    externalFileUri: Uri,
): File {
    lateinit var importedFile: File

    this
        .contentResolver
        .openInputStream(externalFileUri)
        ?.use { inputStream ->
            importedFile = File(this.getExternalFilesDir("Imports"), "importedFile.json")
            importedFile.parentFile?.clearDirectory()
            val outputStream: OutputStream = FileOutputStream(importedFile)
            inputStream.copyTo(outputStream)
        }

    return importedFile
}