package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1

import com.mateuszcholyn.wallet.frontend.view.screen.backup.BackupObjectMapper


object BackupV1JsonReader {

    fun readBackupWalletV1(jsonContent: String): BackupWalletV1 =
        BackupObjectMapper
            .objectMapper
            .readValue(jsonContent, BackupWalletV1::class.java)

}
