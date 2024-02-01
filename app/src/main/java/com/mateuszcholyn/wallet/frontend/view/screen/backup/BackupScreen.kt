package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export.BackupExport
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo.BackupImport
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
fun BackupDataScreen() {
    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        // TODO: dodaj testy na import pliku, że jakis skopany
        // TODO: dopisz export do CSV
        // TODO: zrob tak, zeby było podsumowanie osobno
        BackupImport()
        BackupExport()
    }
}

@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    BackupDataScreen()
}

