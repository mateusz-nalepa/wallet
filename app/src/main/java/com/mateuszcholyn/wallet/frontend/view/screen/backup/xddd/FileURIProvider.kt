package com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

fun onFileSelected(onFileSelectedAction: (Uri) -> Unit): (Uri) -> Unit =
    onFileSelectedAction

@Composable
fun newFileSelector(onNewFileSaved: (Uri) -> Unit): NewFileCreatedResult {
    val saveFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val pickedUri = it.data?.data ?: return@rememberLauncherForActivityResult
            onNewFileSaved.invoke(pickedUri)
        }

    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        .apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/txt"
            putExtra(Intent.EXTRA_TITLE, "export.dat")
        }
    return NewFileCreatedResult { saveFileLauncher.launch(intent) }
}

data class NewFileCreatedResult(
    private val action: () -> Unit,
) {
    fun saveContent() {
        action.invoke()
    }
}

@Composable
fun selectedFileReader(onExistingFileSelected: (Uri) -> Unit): ExistingFileSelectedResult {
    val openFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val pickedUri = it.data?.data ?: return@rememberLauncherForActivityResult
            onExistingFileSelected.invoke(pickedUri)
        }

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        .apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }



    return ExistingFileSelectedResult { openFileLauncher.launch(intent) }
}

data class ExistingFileSelectedResult(
    private val action: () -> Unit,
) {
    fun readFile() {
        action.invoke()
    }
}