package com.mateuszcholyn.wallet.frontend.view.screen.backup

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

object BackupObjectMapper {

    val objectMapper: ObjectMapper =
        ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}