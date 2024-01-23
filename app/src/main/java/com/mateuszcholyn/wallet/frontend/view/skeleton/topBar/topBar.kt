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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val demoAppSwitcher: DemoAppSwitcher,
) : ViewModel() {
    fun isDemoModeEnabled(): Boolean =
        demoAppSwitcher.isDemoModeEnabled()
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    topBarViewModel: TopBarViewModel = hiltViewModel(),
) {
    val isDemoModeEnabled by rememberSaveable { mutableStateOf(topBarViewModel.isDemoModeEnabled()) }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primarySurface,
        title = {
            TopBarStatelessContent(isDemoModeEnabled)
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
}

//@Preview(showBackground = true)
//@Composable
//fun TopBarPreview() {
//    val scope = rememberCoroutineScope()
//    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
//    TopBar(scope = scope, scaffoldState = scaffoldState)
//}
