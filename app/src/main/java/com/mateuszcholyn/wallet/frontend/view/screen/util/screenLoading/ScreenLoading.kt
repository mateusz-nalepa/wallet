package com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun ScreenLoading() {
    Column(
        modifier = defaultModifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenLoadingLightPreview() {
    SetContentOnLightPreview {
        ScreenLoading()
    }
}
