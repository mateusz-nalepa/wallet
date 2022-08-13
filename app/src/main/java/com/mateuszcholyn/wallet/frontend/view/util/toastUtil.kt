package com.mateuszcholyn.wallet.frontend.view.util

import android.content.Context
import android.widget.Toast

fun showShortText(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT,
    ).show()
}


fun showLongText(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_LONG,
    ).show()
}