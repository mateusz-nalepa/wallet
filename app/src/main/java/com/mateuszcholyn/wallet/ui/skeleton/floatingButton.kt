package com.mateuszcholyn.wallet.ui.skeleton

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mateuszcholyn.wallet.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FloatingButton(scope: CoroutineScope, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null && currentRoute.contains(NavDrawerItem.AddOrEditExpense.routeWithoutId())) {
        return
    }

    ExtendedFloatingActionButton(
            icon = { Icon(Icons.Filled.ShoppingCart, "") },
            text = { Text(stringResource(R.string.addExpense)) },
            onClick = {
                scope.launch {
                    navController.navigate(NavDrawerItem.AddOrEditExpense.routeWithoutId()) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
//                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
//                        restoreState = true
                    }

                }
            },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}