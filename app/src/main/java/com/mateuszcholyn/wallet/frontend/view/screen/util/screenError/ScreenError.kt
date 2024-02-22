package com.mateuszcholyn.wallet.frontend.view.screen.util.screenError

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun ScreenError(errorMessage: String) {
    Column(
        modifier = defaultModifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(Icons.Rounded.Error, stringResource(R.string.iconError))
        Text(text = EMPTY_STRING)
        Text(
            fontSize = 24.sp,
            text = errorMessage,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenErrorLightPreview() {
    SetContentOnLightPreview {
        ScreenError("something went wrong")
    }
}
