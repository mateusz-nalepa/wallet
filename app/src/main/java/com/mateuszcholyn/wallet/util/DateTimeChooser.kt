package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.TextView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeChooser(
    private var now: LocalDateTime,
    private var activity: Activity,
    private var dateTextView: TextView
) {

    private var time: LocalTime = LocalTime.now()
    private var date: LocalDate = LocalDate.now()

    private val textListener =
        View.OnClickListener {
            showDatePickerDialog()
        }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            activity,
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
            activity,
            mTimeDataSet,
            now.hour,
            now.minute,
            true
        ).show()

    }

    private val mTimeDataSet = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
        time = LocalTime.of(hourOfDay, minute)
        dateTextView.text = LocalDateTime.of(date, time).toHumanText()
    }

    init {
        dateTextView.setOnClickListener(textListener)
    }

}