package com.mateuszcholyn.wallet.ui.skeleton

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.screen.DummyScreen
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.NewAddOrEditExpenseScreen
import com.mateuszcholyn.wallet.ui.screen.category.NewCategoryScreen
import com.mateuszcholyn.wallet.ui.screen.summary.NewSummaryScreen

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Category : NavDrawerItem("category", R.drawable.ic_home, "Category")
    object AddOrEditExpense : NavDrawerItem("addOrEditExpense?expenseId={expenseId}", R.drawable.ic_music, "AddOrEditExpense")
    object SummaryScreen : NavDrawerItem("summary", R.drawable.ic_movie, "Summary")
    object Dummy : NavDrawerItem("dummy", R.drawable.ic_book, "Dummy")
}

fun NavDrawerItem.AddOrEditExpense.routeWithoutId(): String =
        "addOrEditExpense"

fun NavDrawerItem.AddOrEditExpense.routeWithId(expenseId: Long): String =
        "addOrEditExpense?expenseId=$expenseId"


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.SummaryScreen.route) {
        composable(
                route = NavDrawerItem.AddOrEditExpense.route,
                arguments = listOf(navArgument("expenseId") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }),
        ) { backStackEntry ->
            NewAddOrEditExpenseScreen(navController = navController, backStackEntry.arguments?.getString("expenseId"))
        }
        composable(NavDrawerItem.Category.route) {
            NewCategoryScreen()
        }
        composable(NavDrawerItem.SummaryScreen.route) {
            NewSummaryScreen(navController = navController)
        }
        composable(NavDrawerItem.Dummy.route) {
            DummyScreen()
        }
    }
}
