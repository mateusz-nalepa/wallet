package com.mateuszcholyn.wallet.frontend.view.skeleton.topBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class TopBarUiState(
    val isDemoModeEnabled: Boolean = false,
)

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    topBarViewModel: TopBarViewModel = hiltViewModel(),
) {
    DisposableEffect(key1 = Unit, effect = {
        topBarViewModel.loadTopBarState()
        onDispose { }
    })

    val topBarUiState by remember { topBarViewModel.exposedTopBarUiState }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primarySurface,
        title = {
            TopBarContentStateless(topBarUiState.isDemoModeEnabled)
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, EMPTY_STRING)
            }
        },
    )

    TopBarStateless(
        topBarUiState = topBarUiState,
        onDrawerOpen = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        },
    )
}

@Composable
fun TopBarStateless(
    topBarUiState: TopBarUiState,
    onDrawerOpen: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primarySurface,
        title = {
            TopBarContentStateless(topBarUiState.isDemoModeEnabled)
        },
        navigationIcon = {
            IconButton(onClick = onDrawerOpen) {
                Icon(Icons.Filled.Menu, EMPTY_STRING)
            }
        },
    )
}
