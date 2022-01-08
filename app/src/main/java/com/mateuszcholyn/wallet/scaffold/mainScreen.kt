package com.mateuszcholyn.wallet.scaffold

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.view.showShortText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
            floatingActionButton = { FloatingButton(scope = scope) },
    ) {
        Navigation(navController = navController)
    }
    // }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun FloatingButton(scope: CoroutineScope) {
    FloatingActionButton(onClick = {
        scope.launch {
            showShortText("Robię XDDDDDDDD")
        }
    }) {
        Text("+")
    }
}