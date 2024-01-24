package com.mateuszcholyn.wallet.manager.validator

import java.time.Instant

object LocalDateTimeValidator {


    fun assertInstant(
        actual: Instant,
        expected: Instant,
        messageProvider: () -> Any = { }
    ) {
//        /**
//         * When saving to DB only seconds are saved and nano precision is lost
//         */
        val actualWithoutPrecision = actual.toEpochMilli()
        val expectedWithoutPrecision = expected.toEpochMilli()

        assert(actualWithoutPrecision == expectedWithoutPrecision) {
            messageProvider.invoke()
        }

    }

}