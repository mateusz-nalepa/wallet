package com.mateuszcholyn.wallet

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI


class SummaryActivity : AppCompatActivity(), DIAware {

    override val di by closestDI()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textArray = arrayOf("One", "Two", "Three", "Four")
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        linearLayout.orientation = LinearLayout.VERTICAL
        for (i in textArray.indices) {
            val textView = TextView(this)
            textView.text = textArray[i]
            linearLayout.addView(textView)
        }

    }
}