package com.mateuszcholyn.wallet.expense.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()

    private val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
    private lateinit var mCalendar: Calendar
    private lateinit var activity: Activity
    private lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_activity)
        initializeInjector()
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

        val category = findViewById<Spinner>(R.id.category_spinner).selectedItem as String
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString().toDouble()
        val description = findViewById<EditText>(R.id.description).text.toString().toString()
        val date = dateAsGregorianCalendar()

        val savedExpenseDto = ExpenseDto(
                amount = expenseAmount,
                category = category,
                date = date,
                active = true,
                description = description
        ).let {
            expenseService.addExpense(it)
        }

//        dbOperations()
        Toast
                .makeText(applicationContext, "Id od expense ${savedExpenseDto.id}", Toast.LENGTH_SHORT)
                .show()

        val intent = Intent(this, ExpenseHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun dateAsGregorianCalendar(): Calendar {
        val stringDate = this.date.text.toString()
        val date = simpleDateFormat.parse(stringDate)
        val gregorianCalendar = GregorianCalendar.getInstance()
        gregorianCalendar.time = date
        return gregorianCalendar
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}
