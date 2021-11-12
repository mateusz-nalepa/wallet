package com.mateuszcholyn.wallet.view

import android.widget.Toast
import com.mateuszcholyn.wallet.config.ApplicationContext

fun showShortText(text: String) {
    Toast.makeText(
        ApplicationContext.appContext,
        text,
        Toast.LENGTH_SHORT
    ).show()
}