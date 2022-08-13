package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalDoubleTypeConverter {

    @TypeConverter
    fun toDouble(input: BigDecimal?): Double =
        input
            ?.toDouble()
            ?: 0.0

    @TypeConverter
    fun doubleToBigDecimal(input: Double?): BigDecimal =
        input
            ?.let { BigDecimal.valueOf(input) }
            ?: BigDecimal.ZERO

}