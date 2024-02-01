package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupObjectMapper
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1


object BackupV1JsonReader {

    fun readBackupWalletV1(jsonContent: String): BackupWalletV1 =
        BackupObjectMapper
            .objectMapper
            .readValue(jsonContent, BackupWalletV1::class.java)

}
