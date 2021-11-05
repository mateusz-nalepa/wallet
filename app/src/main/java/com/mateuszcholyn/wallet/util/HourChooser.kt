package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.TextView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HourChooser(
    private var now: LocalDateTime,
    private var activity: Activity,
    private var dateTextView: TextView
) {

    private var time: LocalTime = LocalTime.now()
    private var date: LocalDate = LocalDate.now()

    private val textListener = View.OnClickListener {
        DatePickerDialog(
            activity,
            mDateDataSet,
            now.year,
            now.monthValue,
            now.dayOfMonth
        )
            .show()
    }

    private val mDateDataSet = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        date = LocalDate.of(year, month, day)

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
        val data = LocalDateTime.of(date, time)
        dateTextView.text = simpleDateFormat.format(data)
    }

    init {
        dateTextView.setOnClickListener(textListener)
    }

}