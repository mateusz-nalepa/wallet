package com.mateuszcholyn.wallet.scaffold

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.scaffold.screens.*

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Category : NavDrawerItem("category", R.drawable.ic_home, "Category")
    object AddOrEditExpense : NavDrawerItem("addOrEditExpense", R.drawable.ic_music, "AddOrEditExpense")
    object SummaryScreen : NavDrawerItem("summary", R.drawable.ic_movie, "Summary")
    object Books : NavDrawerItem("books", R.drawable.ic_book, "Books")
    object Profile : NavDrawerItem("profile", R.drawable.ic_profile, "Profile")
    object Settings : NavDrawerItem("settings", R.drawable.ic_settings, "Settings")
}

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.SummaryScreen.route) {
        composable(NavDrawerItem.Category.route) {
            NewCategoryScreen()
        }
        composable(NavDrawerItem.AddOrEditExpense.route) {
            NewAddOrEditExpenseScreen()
        }
        composable(NavDrawerItem.SummaryScreen.route) {
            NewSummaryScreen()
        }
        composable(NavDrawerItem.Books.route) {
            BooksScreen()
        }
        composable(NavDrawerItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
    }
}