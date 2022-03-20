package com.mateuszcholyn.wallet.ui.util

import android.widget.Toast
import com.mateuszcholyn.wallet.config.ApplicationContext

fun showShortText(text: String) {
    Toast.makeText(
            ApplicationContext.appContext,
            text,
            Toast.LENGTH_SHORT
    ).show()
}