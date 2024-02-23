package com.mateuszcholyn.wallet.frontend.view.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomBarContent(scope: CoroutineScope, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null && currentRoute.contains(expenseFormRoute())) {
        return
    }

    BottomAppBar(
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
    ) {
        BottomBarContentStateless(
            onClick = {
                scope.launch {
                    navController.navigate(expenseFormRoute()) {
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

            }
        )
    }
}

@Composable
fun BottomBarContentStateless(
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        ExtendedFloatingActionButton(
            icon = { Icon(Icons.Filled.ShoppingCart, EMPTY_STRING) },
            text = { Text(stringResource(R.string.button_addExpense)) },
            onClick = onClick,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
    }
}