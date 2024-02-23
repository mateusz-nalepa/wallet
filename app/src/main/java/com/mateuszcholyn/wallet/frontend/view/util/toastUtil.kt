package com.mateuszcholyn.wallet.frontend.view.util

import android.content.Context
import android.widget.Toast


var toast: Toast? = null

fun showShortText(context: Context, text: String) {
    showToast(context, text, Toast.LENGTH_SHORT)
}


fun showLongText(context: Context, text: String) {
    showToast(context, text, Toast.LENGTH_LONG)
}

private fun showToast(context: Context, text: String, length: Int) {
    if (toast != null) {
        toast?.cancel()
        toast = Toast.makeText(context, text, length)
        toast?.show()
    } else {
        toast = Toast.makeText(context, text, length)
        toast?.show()
    }
}