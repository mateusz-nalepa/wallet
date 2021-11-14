package com.mateuszcholyn.wallet.view

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.mateuszcholyn.wallet.util.currentDateAsString
import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import com.mateuszcholyn.wallet.util.toHumanText
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class QuickRangeSelectedListener(
    private var mBeginDate: TextView,
    private var mEndDate: TextView,
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        QuickRange.modifyBasedOnPosition(position, mBeginDate, mEndDate)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


object QuickRange {

    private val quickRanges = mapOf(
        0 to "Dzisiaj",
        1 to "Wczoraj",
        2 to "Przedwczoraj",
        3 to "Ostatni tydzień",
        4 to "Ostatni Miesiąc",
        5 to "Ostatnie 3 Miesiące",
        6 to "Wszystkie wydatki",
    )

    fun quickRangesNames(): List<String> =
        quickRanges.values.toList()

    fun modifyBasedOnPosition(position: Int, mBeginDate: TextView, mEndDate: TextView) {
        when (position) {
            0 -> { // dzisiaj
                val dayBeginning = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                mBeginDate.text = dayBeginning.toHumanText()
                mEndDate.text = currentDateAsString()
            }
            1 -> { // wczoraj
                val yesterDayBeginning = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX)

                mBeginDate.text = yesterDayBeginning.toHumanText()
                mEndDate.text = yesterDayEnd.toHumanText()
            }
            2 -> { // przedwczoraj
                val yesterDayBeginning = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX)

                mBeginDate.text = yesterDayBeginning.toHumanText()
                mEndDate.text = yesterDayEnd.toHumanText()
            }
            3 -> { // ostatni tydzien
                mBeginDate.text =  LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            4 -> {
                mBeginDate.text = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            5 -> {
                mBeginDate.text = LocalDateTime.now().minusMonths(3).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            6 -> {
                mBeginDate.text = minDate.toHumanText()
                mEndDate.text = maxDate.toHumanText()
            }
        }
    }

    fun setDefaultDates(mBeginDate: TextView, mEndDate: TextView) {
        mBeginDate.text = LocalDateTime.now().minusDays(7).toHumanText()
        mEndDate.text = currentDateAsString()
    }

}