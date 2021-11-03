package com.mateuszcholyn.wallet

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayMessageActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setTextToTextView(textView, EXTRA_MESSAGE)
    }

    private fun init() {
        setContentView(R.layout.activity_display_message)
        this.textView = findViewById(R.id.textView)
    }

    private fun setTextToTextView(textView: TextView, messageCode: String) {
        val message = intent.getStringExtra(messageCode)
        textView.apply { text = message }
    }
}
