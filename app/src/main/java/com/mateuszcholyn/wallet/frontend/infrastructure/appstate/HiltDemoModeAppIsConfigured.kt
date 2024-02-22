package com.mateuszcholyn.wallet.frontend.infrastructure.appstate

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.domain.appstate.DemoModeAppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer


// TODO: popraw ogólnie wszystkie błędy kompilacji
class HiltDemoModeAppIsConfigured(
    allBackendServices: AllBackendServices,
    demoModeInitializer: DemoModeInitializer,
) : DemoModeAppIsConfigured