package com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


class FileSelectorLauncher(
    private val launcher: () -> Unit,
) {
    fun launch() {
        launcher.invoke()
    }
}

@Composable
fun fileSelector(
    onExternalFileSelected: (Uri) -> Unit,
): FileSelectorLauncher {

    val filePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri == null) {
                return@rememberLauncherForActivityResult
            }
            onExternalFileSelected.invoke(uri)
        }

    return FileSelectorLauncher {
        filePickerLauncher.launch("application/json")
    }
}

