package com.mateuszcholyn.wallet.view

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.initSpinner(spinnerId: Int, items: List<String>): Spinner {
    val spinner: Spinner = findViewById(spinnerId)

    ArrayAdapter(
        this,
        R.layout.simple_spinner_item,
        items
    ).also { adapter ->
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    return spinner
}