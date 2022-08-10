package com.mateuszcholyn.wallet.infrastructure.util

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalDoubleTypeConverter {

    @TypeConverter
    fun bigDecimalToDouble(input: BigDecimal?): Double =
        input
            ?.toDouble()
            ?: 0.0

    @TypeConverter
    fun doubleToBigDecimal(input: Double?): BigDecimal =
        input
            ?.let { BigDecimal.valueOf(input) }
            ?: BigDecimal.ZERO

}