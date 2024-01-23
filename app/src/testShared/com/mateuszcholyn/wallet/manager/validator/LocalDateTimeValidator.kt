package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.util.localDateTimeUtils.toLocalDateTime
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toMillis
import java.time.LocalDateTime

object LocalDateTimeValidator {

    fun assertLocalDateTime(
        actual: LocalDateTime,
        expected: LocalDateTime,
        messageProvider: () -> Any = { }
    ) {
        /**
         * When saving to DB only seconds are saved and nano precision is lost
         */
        val actualWithoutPrecision = actual.toMillis().toLocalDateTime()
        val expectedWithoutPrecision = expected.toMillis().toLocalDateTime()

        assert(expectedWithoutPrecision == actualWithoutPrecision) {
            messageProvider.invoke()
        }

    }

}