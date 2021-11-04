package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.TextView
import java.util.*

class HourChooser(
    private var mCalendar: Calendar,
    private var activity: Activity,
    private var date: TextView
) {

    private val textListener = View.OnClickListener {
        mCalendar = Calendar.getInstance()
        DatePickerDialog(
            activity,
            mDateDataSet,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    private val mDateDataSet = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, day)
        TimePickerDialog(
            activity,
            mTimeDataSet,
            mCalendar.get(Calendar.HOUR_OF_DAY),
            mCalendar.get(Calendar.MINUTE),
            true
        ).show()

    }

    private val mTimeDataSet = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mCalendar.set(Calendar.MINUTE, minute)
        date.text = simpleDateFormat.format(mCalendar.time)
    }

    init {
        date.setOnClickListener(textListener)
    }

}