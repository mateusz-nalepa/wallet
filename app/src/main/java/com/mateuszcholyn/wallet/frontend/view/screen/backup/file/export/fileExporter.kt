package com.mateuszcholyn.wallet.frontend.view.screen.backup.file.export

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.WalletMediaType
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.clearDirectory
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import java.io.File
import java.io.FileOutputStream

class FileExporterLauncher(
    private val launcher: () -> Unit,
) {
    fun launch() {
        launcher.invoke()
    }
}

@Composable
fun fileExporter(
    fileName: String,
    fileContent: String,
    title: String,
    mediaType: String = WalletMediaType.APPLICATION_JSON,
): FileExporterLauncher {
    val context = currentAppContext()

    return FileExporterLauncher {
        try {
            unsafeFileExporter(
                context = context,
                fileName = fileName,
                fileContent = fileContent,
                title = title,
                mediaType = mediaType,
            )
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}

private fun unsafeFileExporter(
    context: Context,
    fileName: String,
    fileContent: String,
    title: String,
    mediaType: String,
) {
    val file = File(context.getExternalFilesDir("Exports"), fileName)
    file.parentFile?.clearDirectory()

    FileOutputStream(file)
        .use { it.write(fileContent.toByteArray()) }

    val fileUri =
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

    val sendIntent: Intent =
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, fileUri)
            type = mediaType
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
    val shareIntent = Intent.createChooser(sendIntent, fileName)

    context.startActivity(
        Intent.createChooser(
            shareIntent,
            title,
        )
    )

}
