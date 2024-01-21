package com.mateuszcholyn.wallet.frontend.view.skeleton

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense.NewAddOrEditExpenseScreen
import com.mateuszcholyn.wallet.frontend.view.screen.category.NewCategoryScreen
import com.mateuszcholyn.wallet.frontend.view.screen.dummy.DummyScreen
import com.mateuszcholyn.wallet.frontend.view.screen.settings.SettingsScreen
import com.mateuszcholyn.wallet.frontend.view.screen.summary.NewSummaryScreen
import com.mateuszcholyn.wallet.frontend.view.wellness.WellnessScreenRunner

// TODO: translate - how to get context?
sealed class NavDrawerItem(var route: String, var icon: Int, var titleTranslationKey: Int) {
    object Category : NavDrawerItem("category", R.drawable.ic_home, R.string.menuItem_Category)
    object AddOrEditExpense : NavDrawerItem(
        "addOrEditExpense?expenseId={expenseId}&mode={mode}",
        R.drawable.ic_music,
        R.string.menuItem_AddOrEditExpense
    )

    object SummaryScreen : NavDrawerItem("summary", R.drawable.ic_movie, R.string.menuItem_Summary)
    object Settings : NavDrawerItem("settings", R.drawable.ic_book, R.string.menuItem_Settings)
    object Wellness : NavDrawerItem("wellness", R.drawable.ic_book, R.string.menuItem_Wellness)
    object Dummy : NavDrawerItem("dummy", R.drawable.ic_book, R.string.menuItem_Dummy)
}

fun NavDrawerItem.AddOrEditExpense.routeWithoutId(): String =
    "addOrEditExpense"

fun NavDrawerItem.AddOrEditExpense.routeWithId(expenseId: String): String =
    "addOrEditExpense?expenseId=$expenseId"

fun NavDrawerItem.AddOrEditExpense.copyExpense(expenseId: String): String =
    "addOrEditExpense?mode=copy&expenseId=$expenseId"

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = NavDrawerItem.SummaryScreen.route) {
        composable(
            route = NavDrawerItem.AddOrEditExpense.route,
            arguments = listOf(
                navArgument("expenseId") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("mode") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }

            ),
        ) { backStackEntry ->
            NewAddOrEditExpenseScreen(
                onFormSubmitNavigate = { navController.navigate(NavDrawerItem.SummaryScreen.route) },
                onMissingCategoriesNavigate = { navController.navigate(NavDrawerItem.Category.route) },
                actualExpenseId = backStackEntry.arguments?.getString("expenseId"),
                screenMode = backStackEntry.arguments?.getString("mode")
            )
        }
        composable(NavDrawerItem.Category.route) {
            NewCategoryScreen()
        }
        composable(NavDrawerItem.SummaryScreen.route) {
            NewSummaryScreen(navController = navController)
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
        composable(NavDrawerItem.Wellness.route) {
            WellnessScreenRunner()
        }
        composable(NavDrawerItem.Dummy.route) {
            DummyScreen()
        }
    }
}