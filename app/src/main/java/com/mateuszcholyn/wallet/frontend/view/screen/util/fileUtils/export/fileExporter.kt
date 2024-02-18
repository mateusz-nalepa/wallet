package com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export

import android.content.Intent
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.WalletMediaType
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext

data class FileExportParameters(
    val fileName: String,
    val fileContent: String,
    val title: String,
    val mediaType: String = WalletMediaType.APPLICATION_JSON,
)

class FileExporterLauncher(
    private val launcher: (FileExportParameters) -> Unit,
) {
    fun launch(fileExportParameters: FileExportParameters) {
        launcher.invoke(fileExportParameters)
    }
}

@Composable
fun fileExporter(): FileExporterLauncher {
    val context = currentAppContext()

    return FileExporterLauncher { fileExportParameters ->
        val fileUri =
            context.internalFileToExternal(
                fileName = fileExportParameters.fileName,
                fileContent = fileExportParameters.fileContent,
            )

        val sendIntent: Intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, fileUri)
                type = fileExportParameters.mediaType
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
        val shareIntent = Intent.createChooser(sendIntent, fileExportParameters.fileName)

        context.startActivity(
            Intent.createChooser(
                shareIntent,
                fileExportParameters.title,
            )
        )
    }
}
