package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.export.FileExportParameters
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText
import java.time.LocalDateTime


@Composable
fun BackupImport(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {

    val categoryChangedModal = categoryChangedModal()
    val expenseChangedModal = expenseChangedModal()

    val fileSelector =
        fileSelector(
            onFileSelected = { file ->
                backupScreenViewModel.importBackupV1JsonString(
                    fileWithBackupCopy = file,
                    onCategoryChangedAction = { categoryChangedModal.open(it) },
                    onExpanseChangedAction = { expenseChangedModal.open(it) },
                )
            }
        )

    Button(
        onClick = { fileSelector.launch() },
        modifier = defaultButtonModifier,
    ) {
        Text(text = "Importuj dane")
    }
}


@Composable
fun BackupExport(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {
    val fileExporter = fileExporter()

    var isExportEnabled by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isExportEnabled = true
            backupScreenViewModel.createBackupV1JsonString { fileContent ->
                fileExporter.launch(FileExportParameters(
                    fileName = "wallet-backup-${LocalDateTime.now().toHumanDateTimeText()}.json",
                    fileContent = fileContent,
                    title = "Eksport danych",
                ))
                isExportEnabled = false
            }
        },
        modifier = defaultButtonModifier,
    ) {
        if (isExportEnabled) {
            CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
        } else {
            Text("Eksportuj dane")
        }
    }
}

@Composable
fun BackupDataScreen(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {
    val context = currentAppContext()
    val importState by remember { backupScreenViewModel.importState }

    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        // TODO: obtestuj to jakoś?? zrób jakieś error handling? XD
        BackupImport()
        BackupExport()

        when (val importStateTemp = importState) {
            is ImportState.NotStarted -> {}
            is ImportState.Error -> showLongText(context, importStateTemp.errorMessage)
            // TODO: pokaż podsumowanie jakoś bardziej prawilnie XD
            is ImportState.Success ->
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = importStateTemp.importV1Summary.toString()
                )

            is ImportState.Loading ->
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = "Loading"
                )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    BackupDataScreen()
}

