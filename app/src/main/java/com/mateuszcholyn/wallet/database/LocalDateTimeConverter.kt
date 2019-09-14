package com.mateuszcholyn.wallet.database

import android.arch.persistence.room.TypeConverter
import org.joda.time.LocalDateTime


class LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime {
        return LocalDateTime(value)
    }

    @TypeConverter
    fun toLong(value: LocalDateTime): Long {
        return value.toDateTime().millis
    }

}