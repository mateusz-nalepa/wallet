package com.mateuszcholyn.wallet.domain.expense.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.model.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.domain.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.HourChooser
import com.mateuszcholyn.wallet.util.defaultSearchCriteria
import com.mateuszcholyn.wallet.util.simpleDateFormat
import com.mateuszcholyn.wallet.util.toLocalDateTime
import java.util.*

const val ALL_CATEGORIES = "Wszystkie"

class AverageExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private lateinit var activity: Activity
    private var mBeginCalendar: Calendar = Calendar.getInstance()
    private var mEndCalendar: Calendar = Calendar.getInstance()
    private lateinit var mBeginDate: TextView
    private lateinit var mEndDate: TextView
    private lateinit var resultList: List<ExpenseDto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        activity = this
        title = "Oblicz średni wydatek"
        setContentView(R.layout.activity_average_expense)
        initCategorySpinner()
        initDateTimePickers()
        resultList = expenseService.getAll(defaultSearchCriteria())
        calculate()
    }

    private fun initDateTimePickers() {
        initBeginDateTimePicker()
        initEndDateTimePicker()
    }

    private fun initBeginDateTimePicker() {
        mBeginDate = findViewById(R.id.average_begin_dateTimePicker)
        val gregorianCalendar = GregorianCalendar()
        gregorianCalendar.add(Calendar.DAY_OF_WEEK, -7)
        mBeginDate.text = simpleDateFormat.format(gregorianCalendar.time)
        HourChooser(mBeginCalendar, activity, mBeginDate)
    }

    private fun initEndDateTimePicker() {
        mEndDate = findViewById(R.id.average_end_dateTimePicker)
        mEndDate.text = simpleDateFormat.format(GregorianCalendar().time)
        HourChooser(mEndCalendar, activity, mEndDate)
    }

    fun calculateAverageAmount(view: View) {
        calculate()
        Toast.makeText(ApplicationContext.appContext, "Kalkulacja zakończona", Toast.LENGTH_SHORT).show()
    }

    private fun calculate() {
        val category = findViewById<Spinner>(R.id.average_category_spinner).selectedItem as String

        val averageAmount = findViewById<TextView>(R.id.averageAmount)
        val averageSearchCriteria =
                AverageSearchCriteria(
                        categoryName = if (category == ALL_CATEGORIES) ALL_CATEGORIES else category,
                        beginDate = mBeginDate.toLocalDateTime(),
                        endDate = mEndDate.toLocalDateTime()
                )

        averageAmount.text = expenseService.averageExpense(averageSearchCriteria).toString() + " zł"
    }

    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.average_category_spinner)
        val allCategories = mutableListOf<String>().apply {
            add(ALL_CATEGORIES)
            addAll(categoryService.getAllNamesOnly())
        }

        ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                allCategories
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
