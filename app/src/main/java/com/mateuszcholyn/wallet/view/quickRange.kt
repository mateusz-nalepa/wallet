package com.mateuszcholyn.wallet.view

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.mateuszcholyn.wallet.util.currentDateAsString
import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import com.mateuszcholyn.wallet.util.toHumanText
import java.time.LocalDateTime

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
        0 to "Ostatni tydzień",
        1 to "Ostatni Miesiąc",
        2 to "Ostatnie 3 Miesiące",
        3 to "Wszystkie wydatki",
    )

    fun quickRangesNames(): List<String> =
        quickRanges.values.toList()

    fun modifyBasedOnPosition(position: Int, mBeginDate: TextView, mEndDate: TextView) {
        when (position) {
            0 -> {
                mBeginDate.text = LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            1 -> {
                mBeginDate.text = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            2 -> {
                mBeginDate.text = LocalDateTime.now().minusMonths(3).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            3 -> {
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