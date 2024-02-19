package com.mateuszcholyn.wallet.frontend.view.screen.settings

import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val demoAppSwitcher: DemoAppSwitcher,
) : ViewModel() {

    fun demoAppSwitcher(): DemoAppSwitcher =
        demoAppSwitcher

}
