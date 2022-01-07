package com.mateuszcholyn.wallet.scaffold.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

val defaultModifier =
        Modifier
                .padding(4.dp)
                .fillMaxWidth()

val defaultButtonModifier =
        Modifier
                .padding(4.dp)
                .height(60.dp)
                .fillMaxWidth()

@Composable
fun <R, T : R> MutableLiveData<T>.observeAsMutableState(initial: R): MutableState<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}