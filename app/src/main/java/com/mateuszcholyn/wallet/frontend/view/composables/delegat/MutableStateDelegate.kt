package com.mateuszcholyn.wallet.frontend.view.composables.delegat

import androidx.compose.runtime.MutableState
import kotlin.reflect.KProperty


class MutableStateDelegate<T>(private var state: MutableState<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return state.value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state.value = value
    }
}
