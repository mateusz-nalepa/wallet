package com.mateuszcholyn.wallet.ui.skeleton

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun BottomBar(
        navController: NavHostController,
) {
    val selectedIndex = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null && currentRoute.contains(NavDrawerItem.AddOrEditExpense.routeWithoutId())) {
        return
    }

    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
                icon = {
                    Icon(imageVector = Icons.Filled.ShoppingCart, "")
                },
                label = { Text(text = "Nowy wydatek") },
                selected = (selectedIndex.value == 0),
                onClick = {
                    scope.launch {
                        navController.navigate(NavDrawerItem.AddOrEditExpense.routeWithoutId()) {
                            selectedIndex.value = 0

                        }
                    }

                }
        )
    }
}

//@Composable
//fun BottomNavigationItem(icon = {
//    //composable function for menu icon
//},
//label = { //composable function for menu title},
//    selected = //mutableState boolean for highlight,
//            onClick = {
//        //menu item click event
//    })
