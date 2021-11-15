package com.mateuszcholyn.wallet

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector


class SummaryActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()

//        setContentView(R.layout.activity_summary)

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

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}