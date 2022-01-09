package com.mateuszcholyn.wallet.scaffold

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.scaffold.screens.DummyScreen
import com.mateuszcholyn.wallet.scaffold.screens.NewAddOrEditExpenseScreen
import com.mateuszcholyn.wallet.scaffold.screens.NewCategoryScreen
import com.mateuszcholyn.wallet.scaffold.screens.NewSummaryScreen

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

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.SummaryScreen.route) {
        composable(
                route = NavDrawerItem.AddOrEditExpense.route,
                arguments = listOf(navArgument("expenseId") { defaultValue = "-1" }),
        ) {backStackEntry ->
            NewAddOrEditExpenseScreen(navController = navController, backStackEntry.arguments?.getString("expenseId")?.toLong() ?: -1)
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
