package com.mateuszcholyn.wallet.frontend.view.skeleton.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.R

// TODO: accessability dla całej apki
@Composable
fun TopBarContentStateless(
    isDemoModeEnabled: Boolean,
) {
    val weight = if (isDemoModeEnabled) 0.5f else 1.0f

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 20.sp,
            modifier = Modifier.weight(weight),
        )
        if (isDemoModeEnabled) {
            Text(
                text = "Demo",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(weight)
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colors.error,
                        shape = RoundedCornerShape(20.dp)
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarContentWithDemoEnabledPreview() {
    MaterialTheme {
        TopBarContentStateless(true)
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarContentWithDemoDisabledPreview() {
    MaterialTheme {
        TopBarContentStateless(false)
    }
}
