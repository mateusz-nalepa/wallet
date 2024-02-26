package com.mateuszcholyn.wallet.frontend.view.screen.about

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Coffee
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.BOTTOM_BAR_HEIGHT
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import com.mateuszcholyn.wallet.frontend.view.util.showShortText
import com.mateuszcholyn.wallet.userConfig.hodorLanguage.HodorLanguageConfig
import com.mateuszcholyn.wallet.userConfig.hodorLanguage.numberOfClicksToHaveHodorLanguage

@Composable
fun AboutScreen() {
    val state = rememberScrollState()

    Column(
        defaultModifier
            .fillMaxSize()
            .verticalScroll(state)
            .background(MaterialTheme.colors.background)
            .padding(PaddingValues(bottom = BOTTOM_BAR_HEIGHT)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EasterEgg()
        Text(
            fontSize = 24.sp,
            text = stringResource(R.string.aboutScreen_author),
            fontWeight = FontWeight.Bold,
        )
        ClickableText(
            text = "Mateusz Nalepa",
            link = "https://www.linkedin.com/in/mateusz-nalepa-5742a112b/",
        )
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = stringResource(R.string.aboutScreen_logoCreator),
            fontWeight = FontWeight.Bold,
        )
        ClickableText(
            text = "Sebastian Kaleta",
            link = "https://www.linkedin.com/in/sebastiankaleta",
        )
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = stringResource(R.string.aboutScreen_consultations),
            fontWeight = FontWeight.Bold,
        )
        ClickableText(
            text = "Piotr Liszka",
            link = "https://www.linkedin.com/in/piotr-liszka-pl",
        )
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = stringResource(R.string.aboutScreen_thanks),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(R.string.aboutScreen_thanks_text),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 50.dp),
        )
        Text(text = "")
        Text(
            fontSize = 24.sp,
            text = stringResource(R.string.aboutScreen_support),
            fontWeight = FontWeight.Bold,
        )
        ClickableText(
            text = stringResource(R.string.aboutScreen_support_text),
            link = "https://www.buymeacoffee.com/mateusz.nalepa",
        )
        Icon(Icons.Rounded.Coffee, stringResource(R.string.icons_iconError))
    }
}

@Composable
private fun EasterEgg() {

    val context = currentAppContext()
    val isHodorAvailable = !HodorLanguageConfig.isHodorLanguageNotAvailable(context)

    var numberOfClicks by remember { mutableIntStateOf(0) }

    val hodorLanguageReadyText = stringResource(id = R.string.hodor_language_available)
    val touchesStill = stringResource(id = R.string.hodor_touches_still)

    val modifier =
        Modifier.clickable {
            if (numberOfClicks + 1 >= numberOfClicksToHaveHodorLanguage) {
                showLongText(context, hodorLanguageReadyText)
                HodorLanguageConfig.setHodorLanguageToAvailable(context)
            } else {
                numberOfClicks++
                showShortText(
                    context,
                    "$touchesStill ${numberOfClicksToHaveHodorLanguage - numberOfClicks}..."
                )
            }
        }

    Text(
        fontSize = 24.sp,
        text = "Easter Egg",
        fontWeight = FontWeight.Bold,
    )
    if (isHodorAvailable) {
        Text(
            text = stringResource(R.string.aboutScreen_easter_egg_unlocked_text),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
    } else {
        Text(
            text = stringResource(R.string.aboutScreen_easter_egg_locked_text),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .then(modifier),
        )
    }
    Text(text = "")
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
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .clickable {
                CustomTabsIntent
                    .Builder()
                    .build()
                    .launchUrl(context, link.toUri())
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