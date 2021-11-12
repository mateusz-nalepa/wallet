package com.mateuszcholyn.wallet

fun <T> List<T>.onEmpty(onEmptyAction: () -> Unit): List<T> {
    if (this.isEmpty()) {
        onEmptyAction()
    }

    return this
}

fun <T> List<T>.onNotEmpty(notEmptyAction: () -> Unit): List<T> {
    if (this.isNotEmpty()) {
        notEmptyAction()
    }

    return this
}

