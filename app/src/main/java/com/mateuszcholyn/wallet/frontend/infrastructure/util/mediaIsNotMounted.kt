package com.mateuszcholyn.wallet.frontend.infrastructure.util

import android.os.Environment

fun mediaIsNotMounted(): Boolean =
    Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()