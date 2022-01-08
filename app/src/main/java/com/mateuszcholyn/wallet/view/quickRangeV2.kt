package com.mateuszcholyn.wallet.view

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import com.mateuszcholyn.wallet.util.*
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
                beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                endDate = LocalDateTime.now(),
            modifyDatesFunction = { mBeginDate, mEndDate ->
                val dayBeginning = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                mBeginDate.value = dayBeginning.toHumanText()
                mEndDate.value = currentDateAsString()
            },
            isDefault = true,
        ),
        QuickRangeDataV2(
            name = "Wczoraj",
                beginDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN),
                endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
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
                beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
                endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
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
                beginDate = LocalDateTime.now().minusDays(7),
                endDate = LocalDateTime.now(),
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.value = currentDateAsString()
            },
        ),
        QuickRangeDataV2(
            name = "Ten Miesiąc",
                beginDate = LocalDateTime.now().atStartOfTheMonth(),
                endDate = LocalDateTime.now(),
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().atStartOfTheMonth().toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
            name = "Ostatni Miesiąc",
                beginDate = LocalDateTime.now().minusMonths(1),
                endDate = LocalDateTime.now(),
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
            name = "Ostatnie 3 Miesiące",
                beginDate = LocalDateTime.now().minusMonths(3),
                endDate = LocalDateTime.now(),
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = LocalDateTime.now().minusMonths(3).toHumanText()
                mEndDate.value = currentDateAsString()
            }
        ),
        QuickRangeDataV2(
                beginDate = minDate,
                endDate = maxDate,
            name = "Wszystkie wydatki",
            modifyDatesFunction = { mBeginDate, mEndDate ->
                mBeginDate.value = minDate.toHumanText()
                mEndDate.value = maxDate.toHumanText()
            }
        )
    )


    fun quickRangesNames(): List<String> =
        quickRangesList.map { it.name }

    fun quickRanges(): List<QuickRangeDataV2> =
            quickRangesList

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
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isDefault: Boolean = false,
)