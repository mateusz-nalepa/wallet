package com.mateuszcholyn.wallet.frontend.view.util

object PercentageCalculator {
    fun calculatePercentage(
        actual: Int,
        total: Int
    ): Int {
        val percentage = (actual.toDouble() / total) * 100
        return percentage.toInt()
    }
}
