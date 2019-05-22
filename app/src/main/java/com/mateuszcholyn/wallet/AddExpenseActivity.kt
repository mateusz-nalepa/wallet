package com.mateuszcholyn.wallet

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.mateuszcholyn.wallet.database.service.DbService
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
    private lateinit var mCalendar: Calendar
    private lateinit var activity: Activity
    private lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_activity)

        activity = this
        initCategorySpinner()
        initDateTimePicker()
    }

    private fun initDateTimePicker() {
        date = findViewById(R.id.dateTimePicker)
        date.text = simpleDateFormat.format(GregorianCalendar().time)
        date.setOnClickListener(textListener)
    }

    private val textListener = View.OnClickListener {
        mCalendar = Calendar.getInstance()
        DatePickerDialog(
                activity,
                mDateDataSet,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.HOUR_OF_DAY)
        ).show()
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

    @SuppressLint("ResourceType")
    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.category_spinner)

        ArrayAdapter.createFromResource(
                this,
                R.array.category_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun addExpense(view: View) {
        val category = findViewById<Spinner>(R.id.category_spinner).selectedItem
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString().toInt()
        val date = this.date.text


        dbOperations()
//        Toast.makeText(applicationContext,"$categoryId", Toast.LENGTH_SHORT).show()
    }

    private fun dbOperations() {
        val dbService = DbService(applicationContext)

//        dbService.saveNewCategory("Mieszkanie")
        val categoryId = dbService.getCategoryId("Mieszkanie")

        Toast.makeText(applicationContext, "$categoryId", Toast.LENGTH_SHORT).show()

    }
}
