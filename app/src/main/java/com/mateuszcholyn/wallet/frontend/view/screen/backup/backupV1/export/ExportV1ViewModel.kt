package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.FileExportParameters
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.internalFileToExternal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExportV1ViewModel @Inject constructor(
    private val exportV1UseCase: ExportV1UseCase,
) : ViewModel() {

    fun exportBackupV1(
        fileName: String,
        context: Context,
        onFileReadyAction: (FileExportParameters) -> Unit,
        onErrorTextProvider: (String) -> Unit,
    ) {
        viewModelScope.launch { // DONE
            try {
                val fileUri =
                    exportV1UseCase
                        .invoke()
                        .let { BackupV1JsonCreator.createBackupWalletV1AsString(it) }
                        .let { context.internalFileToExternal(fileName, fileContent = it) }

                onFileReadyAction.invoke(
                    FileExportParameters(
                        fileName = fileName,
                        fileUri = fileUri,
                        title = "Eksport danych",
                    )
                )
            } catch (t: Throwable) {
                onErrorTextProvider.invoke("Nieznany błąd podczas exportu danych")
            }
        }
    }

}
