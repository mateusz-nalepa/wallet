package com.mateuszcholyn.wallet.frontend.view.screen.backup.file.impo

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.clearDirectory
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class FileSelectorLauncher(
    private val launcher: () -> Unit,
) {
    fun launch() {
        launcher.invoke()
    }
}

@Composable
fun fileSelector(
    onFileSelected: (File) -> Unit,
): FileSelectorLauncher {
    val context = currentAppContext()

    val filePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri == null) {
                return@rememberLauncherForActivityResult
            }

            try {
                unsafeFileImporter(context, uri, onFileSelected)
            } catch (e: Exception) {
                throw e
            }
        }

    return FileSelectorLauncher {
        filePickerLauncher.launch("application/json")
    }
}

private fun unsafeFileImporter(
    context: Context,
    uri: Uri,
    onFileImported: (File) -> Unit,
) {

    context
        .contentResolver
        .openInputStream(uri)
        ?.use { inputStream ->
            val file = File(context.getExternalFilesDir("Imports"), "importedFile.json")
            file.parentFile?.clearDirectory()

            val outputStream: OutputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            onFileImported.invoke(file)
        }
}
