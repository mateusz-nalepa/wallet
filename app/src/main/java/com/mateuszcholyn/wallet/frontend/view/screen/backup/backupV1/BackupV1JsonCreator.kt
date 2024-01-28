package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1

import com.mateuszcholyn.wallet.frontend.view.screen.backup.BackupObjectMapper


object BackupV1JsonCreator {

    private val objectMapper =
        BackupObjectMapper
            .objectMapper
            .writerWithDefaultPrettyPrinter()

    fun createBackupWalletV1AsString(backupWalletV1: BackupWalletV1): String =
        objectMapper.writeValueAsString(backupWalletV1)

}
