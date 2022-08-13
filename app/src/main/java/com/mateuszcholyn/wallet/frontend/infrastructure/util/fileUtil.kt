package com.mateuszcholyn.wallet.frontend.infrastructure.util

import java.io.File

fun String.toFile(): File {
    return File(this)
}

fun File.createNewIfNotExists(): File {

    if (!this.exists()) {
        this.createNewFile()
    }

    return this
}