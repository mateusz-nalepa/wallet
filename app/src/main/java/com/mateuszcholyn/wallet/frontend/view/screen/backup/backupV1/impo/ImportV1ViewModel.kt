package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.externalFileToInternal
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.readFileContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImportV1ViewModel @Inject constructor(
    private val importV1UseCase: ImportV1UseCase,
) : ViewModel() {

    fun importBackupV1(
        context: Context,
        externalFileUri: Uri,
        onCategoryChangedAction: (OnCategoryChangedInput) -> Unit,
        onExpanseChangedAction: (OnExpanseChangedInput) -> Unit,
        onSuccessAction: (ImportV1Summary) -> Unit,
        onErrorTextProvider: (String) -> Unit,
        onImportProgress: (ImportV1Summary) -> Unit,
    ) {
        viewModelScope.launch { // DONE
            try {
                val backupWalletV1 =
                    context
                        .externalFileToInternal(externalFileUri)
                        .readFileContent()
                        .let { BackupV1JsonReader.readBackupWalletV1(it) }
                val importV1Parameters =
                    ImportV1Parameters(
                        onImportProgress = onImportProgress,
                        backupWalletV1 = backupWalletV1,
                        removeAllBeforeImport = false,
                        onCategoryNameChangedAction = onCategoryChangedAction,
                        onExpanseChangedAction = onExpanseChangedAction,
                    )
                val importV1Summary = importV1UseCase.invoke(importV1Parameters)
                onSuccessAction.invoke(importV1Summary)
            } catch (e: Exception) {
                onErrorTextProvider.invoke("Nieznany błąd podczas importu danych")
            }
        }
    }
}
