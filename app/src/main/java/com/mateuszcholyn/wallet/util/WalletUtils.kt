package com.mateuszcholyn.wallet.util

import android.content.Intent
import android.text.Editable
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.mateuszcholyn.wallet.config.ApplicationContext

fun Double.asPrinteableAmount() = run {
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
        Toast.makeText(ApplicationContext.appContext, stringExtra, Toast.LENGTH_SHORT).show()
    }
}


fun EditText.toDouble(): Double {
    return this.text.toString().toDouble()
}

fun EditText.textToString(): String {
    return this.text.toString()
}

fun TextView.toLong(): Long {
    return this.text.toString().toLong()
}

fun Spinner.selectedItemAsString(): String {
    return this.selectedItem as String
}

fun Double.toEditable(): Editable = this.toString().toEditable()
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)