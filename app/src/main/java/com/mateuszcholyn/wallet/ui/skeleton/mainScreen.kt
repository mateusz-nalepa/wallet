package com.mateuszcholyn.wallet.ui.skeleton

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.darkmode.Resolver
import com.mateuszcholyn.wallet.util.darkmode.ThemeProperties

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(themeProperties: ThemeProperties) {
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
    ) {
        Navigation(
                navController = navController,
                themeProperties = themeProperties,
        )
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(ThemeProperties(lightColors(), false, Resolver.LIGHT))
}