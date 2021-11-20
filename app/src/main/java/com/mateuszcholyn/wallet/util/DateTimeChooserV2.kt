package com.mateuszcholyn.wallet.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeChooserV2(
    private var now: LocalDateTime,
    private var context: Context,
    private var dateLiveData: MutableLiveData<String>,
    dateTextView: TextView
) {

    private var time: LocalTime = LocalTime.now()
    private var date: LocalDate = LocalDate.now()

    private val textListener =
        View.OnClickListener {
            showDatePickerDialog()
        }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            context,
            mDateDataSet,
            now.year,
            now.fixedMonthValueMinusOne(),
            now.dayOfMonth
        )
            .show()
    }

    private fun LocalDateTime.fixedMonthValueMinusOne(): Int {
        return this.monthValue - 1
    }

    private val mDateDataSet = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        date = LocalDate.of(year, month + 1, day)

        TimePickerDialog(
            context,
            mTimeDataSet,
            now.hour,
            now.minute,
            true
        ).show()

    }

    private val mTimeDataSet = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
        time = LocalTime.of(hourOfDay, minute)
        dateLiveData.value = LocalDateTime.of(date, time).toHumanText()
    }

    init {
        dateLiveData.value = dateTextView.toLocalDateTime().toHumanText()
        dateTextView.setOnClickListener(textListener)
    }

}