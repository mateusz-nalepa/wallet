package com.mateuszcholyn.wallet.util

import android.os.Environment

fun mediaIsNotMounted(): Boolean =
        Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()