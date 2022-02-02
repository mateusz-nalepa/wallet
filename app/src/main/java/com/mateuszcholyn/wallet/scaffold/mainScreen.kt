package com.mateuszcholyn.wallet.scaffold

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    // If you want the drawer from the right side, uncomment the following
    // CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
            drawerBackgroundColor = colorResource(id = R.color.colorPrimary),
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = { FloatingButton(scope = scope, navController = navController) },
//            bottomBar = {
//                BottomBar(navController = navController)
//            }
    ) {
        Navigation(navController = navController)
    }
    // }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun FloatingButton(scope: CoroutineScope, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null && currentRoute.contains(NavDrawerItem.AddOrEditExpense.routeWithoutId())) {
        return
    }

    ExtendedFloatingActionButton(
            icon = { Icon(Icons.Filled.ShoppingCart, "") },
            text = { Text("Dodaj wydatek") },
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