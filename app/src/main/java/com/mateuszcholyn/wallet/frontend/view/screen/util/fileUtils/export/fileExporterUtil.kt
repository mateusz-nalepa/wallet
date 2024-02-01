package com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.clearDirectory
import java.io.File
import java.io.FileOutputStream


fun Context.internalFileToExternal(
    fileName: String,
    fileContent: String,
): Uri {

    val file = File(this.getExternalFilesDir("Exports"), fileName)
    file.parentFile?.clearDirectory()

    FileOutputStream(file)
        .use { it.write(fileContent.toByteArray()) }

    return FileProvider.getUriForFile(
        this,
        "$packageName.provider",
        file
    )
}
