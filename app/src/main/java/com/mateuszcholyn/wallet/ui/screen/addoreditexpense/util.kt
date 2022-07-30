package com.mateuszcholyn.wallet.ui.screen.addoreditexpense

fun String.isAmountInValid(): Boolean =
    this.isBlank() || this.startsWith("-") || this.cannotConvertToDouble()

private fun String.cannotConvertToDouble(): Boolean =
    !kotlin
        .runCatching {
            this.toDouble()
            true
        }
        .getOrDefault(false)
