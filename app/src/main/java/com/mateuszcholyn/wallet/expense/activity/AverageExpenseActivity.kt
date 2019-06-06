package com.mateuszcholyn.wallet.expense.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.category.service.CategoryService
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.expense.adapter.AverageExpenseAdapter
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.HourChooser
import com.mateuszcholyn.wallet.util.Tablica
import com.mateuszcholyn.wallet.util.simpleDateFormat
import java.util.*

class AverageExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private lateinit var activity: Activity
    private var beginCalendar: Calendar = Calendar.getInstance()
    private var endCalendar: Calendar = Calendar.getInstance()
    private lateinit var beginDate: TextView
    private lateinit var endDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        activity = this
        setContentView(R.layout.activity_average_expense)
        initCategorySpinner()
        initDateTimePickers()
    }

    private fun initDateTimePickers() {
        initBeginDateTimePicker()
        initEndDateTimePicker()
    }

    private fun initBeginDateTimePicker() {
        beginDate = findViewById(R.id.average_begin_dateTimePicker)
        val gregorianCalendar = GregorianCalendar()
        gregorianCalendar.add(Calendar.DAY_OF_WEEK, -7)
        beginDate.text = simpleDateFormat.format(gregorianCalendar.time)
        HourChooser(beginCalendar, activity, beginDate)
    }

    private fun initEndDateTimePicker() {
        endDate = findViewById(R.id.average_end_dateTimePicker)
        endDate.text = simpleDateFormat.format(GregorianCalendar().time)
        HourChooser(endCalendar, activity, endDate)
    }

    fun calculateAverageAmount(view: View) {
        Toast
                .makeText(ApplicationContext.appContext, "Liczę wartość...", Toast.LENGTH_LONG)
                .show()
    }

    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.average_category_spinner)
        val lista = categoryService.getAllNamesOnly()

        ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                lista
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
