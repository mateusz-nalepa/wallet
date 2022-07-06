package com.mateuszcholyn.wallet.ui.wellness

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.ui.util.showShortText

interface Clicker {
    fun show()
}

// TODO show shortText
class DefaultClicker(private val context: Context) : Clicker {
    override fun show() {
        showShortText(context = context, "Default Clicker")
    }
}

@Composable
fun ClickerScreen(modifier: Modifier) {
    Button(
            onClick = { println("Klikles") },
            modifier.padding(top = 8.dp, start = 8.dp),
    ) {
        Text("Clicker")
    }
}