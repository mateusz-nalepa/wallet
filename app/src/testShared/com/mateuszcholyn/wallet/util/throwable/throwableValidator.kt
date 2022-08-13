package com.mateuszcholyn.wallet.util.throwable

import kotlin.reflect.KClass

fun catchThrowable(throwableAction: () -> Unit): Throwable {
    try {
        throwableAction()
    } catch (t: Throwable) {
        return t
    }

    throw RuntimeException("Expected exception, but nothing has been thrown")
}

fun Throwable.validate(validationBlock: ThrowableValidator.() -> Unit) {
    ThrowableValidator(this).apply(validationBlock)
}

class ThrowableValidator(
    private val t: Throwable,
) {

    fun isInstanceOf(expectedClass: KClass<out Throwable>) {
        assert(t::class == expectedClass) {
            "Expected throwable class is: $expectedClass. Actual: ${t::class}"
        }
    }

    fun hasMessage(expectedMessage: String) {
        assert(t.message == expectedMessage) {
            "Expected message is: [$expectedMessage]. Actual: [${t.message}]"
        }
    }

}
