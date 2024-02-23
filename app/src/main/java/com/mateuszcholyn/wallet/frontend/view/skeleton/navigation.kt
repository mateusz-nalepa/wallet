package com.mateuszcholyn.wallet.frontend.view.skeleton

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.screen.about.AboutScreen
import com.mateuszcholyn.wallet.frontend.view.screen.backup.BackupDataScreen
import com.mateuszcholyn.wallet.frontend.view.screen.categoryForm.CategoryFormScreen
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreen
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.ExpenseFormScreen
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreen
import com.mateuszcholyn.wallet.frontend.view.screen.settings.SettingsScreen

sealed class NavDrawerItem(var route: String, var imageVector: ImageVector, var titleTranslationKey: Int) {
    data object Category : NavDrawerItem("category", Icons.Rounded.Category, R.string.menuItem_Category)
    data object AddOrEditExpense : NavDrawerItem(
        "expense-form?expenseId={expenseId}&mode={mode}",
        Icons.Rounded.ShoppingCart,
        R.string.menuItem_AddOrEditExpense
    )

    data object History : NavDrawerItem("history", Icons.Rounded.Summarize, R.string.menuItem_History)
    data object Settings : NavDrawerItem("settings", Icons.Rounded.Settings, R.string.menuItem_Settings)
    data object Backup : NavDrawerItem("backup", Icons.Rounded.Backup, R.string.menuItem_Backup)
    data object About : NavDrawerItem("about", Icons.Rounded.Info, R.string.menuItem_About)
}

fun expenseFormRoute(): String =
    "expense-form"

fun editExpenseRoute(expenseId: ExpenseId): String =
    "expense-form?expenseId=${expenseId.id}"

fun copyExpenseRoute(expenseId: ExpenseId): String =
    "expense-form?mode=copy&expenseId=${expenseId.id}"

fun categoryFormScreenRoute(): String =
    "category-form"

fun categoryFormScreenRoute(categoryId: CategoryId): String =
    "category-form?existingCategoryId=${categoryId.id}"

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
) {
//    NavHost(navController, startDestination = "category-form") {
    NavHost(navController, startDestination = NavDrawerItem.History.route) {
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
            ExpenseFormScreen(
                navHostController = navController,
                actualExpenseId = backStackEntry.arguments?.getString("expenseId"),
                screenMode = backStackEntry.arguments?.getString("mode")
            )
        }
        composable(NavDrawerItem.Category.route) {
            CategoryScreen(navHostController = navController)
        }
        composable(
            route = "category-form?existingCategoryId={existingCategoryId}",
            arguments = listOf(
                navArgument("existingCategoryId") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            CategoryFormScreen(
                existingCategoryId = backStackEntry.arguments?.getString("existingCategoryId"),
                navHostController = navController,
            )
        }
        composable(NavDrawerItem.History.route) {
            HistoryScreen(navHostController = navController)
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
        composable(NavDrawerItem.Backup.route) {
            BackupDataScreen()
        }
        composable(NavDrawerItem.About.route) {
            AboutScreen()
        }
    }
}
