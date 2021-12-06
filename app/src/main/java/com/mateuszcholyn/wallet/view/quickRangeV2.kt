package com.mateuszcholyn.wallet.view

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import com.mateuszcholyn.wallet.util.atStartOfTheMonth
import com.mateuszcholyn.wallet.util.currentDateAsString
import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import com.mateuszcholyn.wallet.util.toHumanText
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class QuickRangeSelectedListenerV2(
    private var mBeginDate: MutableLiveData<String>,
    private var mEndDate: MutableLiveData<String>,
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        QuickRangeV2.modifyBasedOnPosition(position, mBeginDate, mEndDate)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


object QuickRangeV2 {

    private val quickRangesList = listOf(
        QuickRangeDataV2(
            name = "Dzisiaj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val dayBeginning = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                mBeginDate.value = dayBeginning.toHumanText()
                mEndDate.value = currentDateAsString()
            },
            isDefault = true,
        ),
        QuickRangeDataV2(
            name = "Wczoraj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val yesterDayBeginning =
                    LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX)

                mBeginDate.value = yesterDayBeginning.toHumanText()
                mEndDate.value = yesterDayEnd.toHumanText()
            }
        ),
        QuickRangeDataV2(
            name = "Przedwczoraj",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val yesterDayBeginning =
                    LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN)
                val yesterDayEnd = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX)

                mBeginDate.value = yesterDayBeginning.toHumanText()
                mEndDate.value = yesterDayEnd.toHumanText()
            }
        ),
        QuickRangeDataV2(
            name = "Ostatni tydzień",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.value = currentDateAsString()
            },
        ),
        QuickRangeDataV2(
            name = "Ten Miesiąc",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().atStartOfTheMonth().toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
            name = "Ostatni Miesiąc",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
            name = "Ostatnie 3 Miesiące",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusMonths(3).toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
            name = "Wszystkie wydatki",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = minDate.toHumanText()
                mEndDate.value = maxDate.toHumanText()
            }
        )
    )


    fun quickRangesNames(): List<String> =
        quickRangesList.map { it.name }

    fun modifyBasedOnPosition(
        position: Int,
        mBeginDate: MutableLiveData<String>,
        mEndDate: MutableLiveData<String>
    ) {
        val quickRangeData = quickRangesList[position]
        quickRangeData.modifyDatesFunction.invoke(mBeginDate, mEndDate)
    }

    fun setDefaultDates(mBeginDate: MutableLiveData<String>, mEndDate: MutableLiveData<String>) {
        val quickRangeData = requireNotNull(quickRangesList.find { it.isDefault }) {
            "Quick Range Data with default not found"
        }

        quickRangeData.modifyDatesFunction.invoke(mBeginDate, mEndDate)
    }

}

class QuickRangeDataV2(
    val name: String,
    val modifyDatesFunction: (mBeginDate: MutableLiveData<String>, mEndDate: MutableLiveData<String>) -> Unit,
    val isDefault: Boolean = false,
)