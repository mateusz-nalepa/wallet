package com.mateuszcholyn.wallet.frontend.domain.demomode

interface DemoAppSwitcher {

    fun isDemoModeEnabled(): Boolean
    fun switch()
    fun buttonText(): String

}

interface DemoModeEnabledMarker : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        true

    override fun buttonText(): String =
        "Wyjdź z trybu demo"
}


interface DemoModeDisabledMarker : DemoAppSwitcher {
    override fun isDemoModeEnabled(): Boolean =
        false

    override fun buttonText(): String =
        "Uruchom tryb demo"
}
