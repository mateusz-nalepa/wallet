package com.mateuszcholyn.wallet.frontend.view.screen.about

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TagFaces
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun AboutScreen() {
    val state = rememberScrollState()

    Column(
        defaultModifier
            .fillMaxSize()
            .verticalScroll(state)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            fontSize = 24.sp,
            text = "Author",
            fontWeight = FontWeight.Bold,
        )
        Row {
            Icon(Icons.Rounded.TagFaces, stringResource(R.string.iconError))
            Text(text = " ")
            ClickableText(
                text = "Mateusz Nalepa",
                link = "https://copilot.microsoft.com/",
            )
        }
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = "Logo creator",
            fontWeight = FontWeight.Bold,
        )
        Row {
            Icon(Icons.Rounded.TagFaces, stringResource(R.string.iconError))
            Text(text = " ")
            ClickableText(
                text = "Sebastian Kaleta",
                link = "https://copilot.microsoft.com/",
            )
        }
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = "Consulations",
            fontWeight = FontWeight.Bold,
        )
        Row {
            Icon(Icons.Rounded.TagFaces, stringResource(R.string.iconError))
            Text(text = " ")
            ClickableText(
                text = "Piotr Liszka",
                link = "https://copilot.microsoft.com/",
            )
        }
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = "Thanks",
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Special thanks to all my friends for testing and inspirations!",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 50.dp),
        )
        Icon(Icons.Rounded.TagFaces, stringResource(R.string.iconError))
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = "Libraries used",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun ClickableText(
    text: String,
    link: String,
) {
    val context = currentAppContext()

    val annotatedText = AnnotatedString.Builder(text).apply {
        addStyle(SpanStyle(textDecoration = TextDecoration.Underline), 0, length)
        addStringAnnotation(
            tag = "URL",
            annotation = link,
            start = 0,
            end = length
        )
    }.toAnnotatedString()

    Text(
        text = annotatedText,
        fontSize = 16.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.clickable {
            CustomTabsIntent.Builder().build().launchUrl(context, link.toUri())
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AboutScreenLightPreview() {
    SetContentOnLightPreview {
        AboutScreen()
    }
}