package com.mateuszcholyn.wallet.frontend.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

@ExperimentalMaterialApi
@ExperimentalFoundationApi
class CategoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun myFirstTest() {
//        // Start the app
//        val categoryRepository = TestCategoryRepository()
//        composeTestRule.setContent {
//            withDI(di = createDi(categoryRepository)) {
//                MaterialTheme {
//                    ProvideWindowInsets {
//                        val systemUiController = rememberSystemUiController()
//                        val darkIcons = MaterialTheme.colors.isLight
//                        SideEffect {
//                            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
//                        }
//                        MainScreen()
//                    }
//                }
//
//            }
//
//        }
//
//        composeTestRule.goToCategoryScreen()
//
//        composeTestRule.onNodeWithTag("NewCategoryTextField").performTextInput("XDDD")
//        composeTestRule.onNodeWithTag("AddNewCategoryButton").performClick()
//
//        assert(categoryRepository.getAllOrderByUsageDesc().size == 1)
//        composeTestRule.onNodeWithTag("CategoryItem#1").assertExists()
//        composeTestRule.onRoot().captureToImage()
//
//
//    }

}

private fun ComposeContentTestRule.goToCategoryScreen() {
    this.onRoot().performTouchInput { swipeRight() }
    this.onNodeWithText("Category").performClick()
}

