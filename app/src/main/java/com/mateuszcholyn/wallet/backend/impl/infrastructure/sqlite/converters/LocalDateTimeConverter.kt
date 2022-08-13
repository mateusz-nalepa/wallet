package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters

import androidx.room.TypeConverter
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toLocalDateTime
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toMillis
import java.time.LocalDateTime


object LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime =
        value.toLocalDateTime()

    @TypeConverter
    fun toLong(value: LocalDateTime): Long =
        value.toMillis()

}
