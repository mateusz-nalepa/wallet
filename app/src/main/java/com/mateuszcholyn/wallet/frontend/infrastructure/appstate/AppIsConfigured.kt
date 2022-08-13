package com.mateuszcholyn.wallet.frontend.infrastructure.appstate

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoModeInitializer


class HiltAppIsConfigured(
    allBackendServices: AllBackendServices,
    demoModeInitializer: DemoModeInitializer,
) : AppIsConfigured