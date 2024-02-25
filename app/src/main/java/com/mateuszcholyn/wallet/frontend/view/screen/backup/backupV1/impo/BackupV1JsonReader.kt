package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupObjectMapper
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletVersionReader


object BackupV1JsonReader {

    fun readBackupWalletV1(jsonContent: String): BackupWalletV1 {

        val backupWalletVersionReader =
            BackupObjectMapper
                .objectMapper
                .readValue(jsonContent, BackupWalletVersionReader::class.java)

        if (backupWalletVersionReader.version != 1) {
            throw BackupWalletV1NotSupportedVersionException(backupWalletVersionReader.version)
        }

        return BackupObjectMapper
            .objectMapper
            .readValue(jsonContent, BackupWalletV1::class.java)
    }

}


class BackupWalletV1NotSupportedVersionException(
    val version: Int,
) :
    RuntimeException(
        "Not supported backup version. Expected 1. Got: $version"
    )