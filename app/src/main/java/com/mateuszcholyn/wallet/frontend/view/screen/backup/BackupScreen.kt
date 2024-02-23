package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export.BackupExport
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo.BackupImport
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
fun BackupDataScreen() {
    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        BackupImport()
        BackupExport()
    }
}

