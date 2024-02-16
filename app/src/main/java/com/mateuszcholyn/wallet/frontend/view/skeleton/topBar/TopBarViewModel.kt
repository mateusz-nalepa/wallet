package com.mateuszcholyn.wallet.frontend.view.skeleton.topBar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val demoAppSwitcher: DemoAppSwitcher,
) : ViewModel() { // done tests XD

    val exposedTopBarUiState: MutableState<TopBarUiState> = mutableStateOf(TopBarUiState())
    private var topBarUiState by MutableStateDelegate(exposedTopBarUiState)

    fun loadTopBarState() {
        topBarUiState =
            topBarUiState.copy(
                isDemoModeEnabled = demoAppSwitcher.isDemoModeEnabled()
            )
    }

}
