package com.mateuszcholyn.wallet.di.demomode

class IdGenerator {
    var init = 1L

    fun nextNumber(): Long {
        return init.also { init++ }
    }

}