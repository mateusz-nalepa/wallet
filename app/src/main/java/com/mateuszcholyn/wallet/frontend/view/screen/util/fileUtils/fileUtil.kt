package com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils

import java.io.File


fun File.clearDirectory() {
    for (file in this.listFiles() ?: emptyArray()) {
        if (!file.isDirectory) {
            file.delete()
        }
    }
}

object WalletMediaType {
    const val APPLICATION_JSON = "application/json"
    const val TEXT_CSV = "text/csv"
}
