package com.mateuszcholyn.wallet.frontend.infrastructure.demomode

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix


object AppRestartService {


    fun restart(context: Context) {
        // Ogólnie nie działa xD
        // Dodaj info, żeby sobie to ktoś ogarnął ręcznie, że musi otworzyć apkę po tym jak się zamknie XD
        ProcessPhoenix.triggerRebirth(context);
    }

}