package com.mateuszcholyn.wallet.frontend.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class ShotTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shotTest() {
        composeRule.setContent { Greeting("Hello World!") }

        compareScreenshot(composeRule)
    }

}


@Composable
fun Greeting(value: String) {
    Text(text = value)
}

