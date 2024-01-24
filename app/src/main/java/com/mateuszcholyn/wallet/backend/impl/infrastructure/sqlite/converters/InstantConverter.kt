package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters

import androidx.room.TypeConverter
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toInstant
import java.time.Instant


object InstantConverter {

    @TypeConverter
    fun toInstant(value: Long): Instant =
        value.toInstant()

    @TypeConverter
    fun toLong(value: Instant): Long =
        value.toEpochMilli()
}
