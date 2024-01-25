package com.mateuszcholyn.wallet.frontend.infrastructure.appstate

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer


// TODO: popraw ogólnie wszystkie błędy kompilacji
class HiltAppIsConfigured(
    allBackendServices: AllBackendServices,
    demoModeInitializer: DemoModeInitializer,
) : AppIsConfigured