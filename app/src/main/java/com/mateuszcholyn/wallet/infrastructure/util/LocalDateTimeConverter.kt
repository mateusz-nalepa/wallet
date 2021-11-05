package com.mateuszcholyn.wallet.infrastructure.util

import androidx.room.TypeConverter
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.mateuszcholyn.wallet.util.toMillis
import java.time.LocalDateTime


class LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime {
        return value.toLocalDateTime()
    }

    @TypeConverter
    fun toLong(value: LocalDateTime): Long {
        return value.toMillis()
    }

}
