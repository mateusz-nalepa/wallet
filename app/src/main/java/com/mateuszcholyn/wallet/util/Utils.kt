package com.mateuszcholyn.wallet.util

import android.content.Intent
import android.text.Editable
import android.widget.Toast
import com.mateuszcholyn.wallet.config.ApplicationContext

fun Double.asAmount() = run {
    "%.2f"
            .format(this)
            .replace(",", ".")
            .toDouble()
}

fun String.asShortCategoryName(): String {
    return if (this.length > 14) {
        this.substring(0, 11) + " ..."
    } else {
        this
    }
}

fun Intent.showIntentMessageIfPresent(messageKey: String) {
    val stringExtra = this.getStringExtra(messageKey)
    if (stringExtra != null) {
        Toast.makeText(ApplicationContext.appContext, stringExtra, Toast.LENGTH_LONG).show()
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)