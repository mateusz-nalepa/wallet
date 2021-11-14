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

    private val quickRangesList = listOf(
        QuickRangeData(
            position = 0,
            name = "Dzisiaj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val dayBeginning = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                mBeginDate.text = dayBeginning.toHumanText()
                mEndDate.text = currentDateAsString()
            },
            isDefault = true,
        ),
        QuickRangeData(
            position = 1,
            name = "Wczoraj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val yesterDayBeginning =
                    LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX)

                mBeginDate.text = yesterDayBeginning.toHumanText()
                mEndDate.text = yesterDayEnd.toHumanText()
            }
        ),
        QuickRangeData(
            position = 2,
            name = "Przedwczoraj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val yesterDayBeginning =
                    LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX)

                mBeginDate.text = yesterDayBeginning.toHumanText()
                mEndDate.text = yesterDayEnd.toHumanText()
            }
        ),
        QuickRangeData(
            position = 3,
            name = "Ostatni tydzień",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.text = LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.text = currentDateAsString()
            },
        ),
        QuickRangeData(
            position = 4,
            name = "Ostatni Miesiąc",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.text = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.text = currentDateAsString()
            }
        ),
        QuickRangeData(
            position = 5,
            name = "Ostatnie 3 Miesiące",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.text = LocalDateTime.now().minusMonths(3).toHumanText()
                mEndDate.text = currentDateAsString()
            }
        ),
        QuickRangeData(
            position = 6,
            name = "Wszystkie wydatki",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.text = minDate.toHumanText()
                mEndDate.text = maxDate.toHumanText()
            }
        )
    )


    fun quickRangesNames(): List<String> =
        quickRangesList.map { it.name }

    fun modifyBasedOnPosition(position: Int, mBeginDate: TextView, mEndDate: TextView) {
        val quickRangeData = requireNotNull(quickRangesList.find { it.position == position }) {
            "Quick Range Data for given position $position not found"
        }

        quickRangeData.modifyDatesFunction.invoke(mBeginDate, mEndDate)
    }

    fun setDefaultDates(mBeginDate: TextView, mEndDate: TextView) {
        val quickRangeData = requireNotNull(quickRangesList.find { it.isDefault }) {
            "Quick Range Data with default not found"
        }

        quickRangeData.modifyDatesFunction.invoke(mBeginDate, mEndDate)
    }

}

class QuickRangeData(
    val position: Int,
    val name: String,
    val modifyDatesFunction: (mBeginDate: TextView, mEndDate: TextView) -> Unit,
    val isDefault: Boolean = false,
)