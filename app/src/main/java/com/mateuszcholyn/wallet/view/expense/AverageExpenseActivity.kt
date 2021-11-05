package com.mateuszcholyn.wallet.view.expense

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
import com.mateuszcholyn.wallet.domain.expense.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.HourChooser
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.mateuszcholyn.wallet.util.toTextForEditable
import java.time.LocalDateTime

const val ALL_CATEGORIES = "Wszystkie"

class AverageExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private lateinit var activity: Activity
    private lateinit var mBeginDate: TextView
    private lateinit var mEndDate: TextView
    private lateinit var resultList: List<Expense>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        activity = this
        title = "Oblicz średni wydatek"
        setContentView(R.layout.activity_average_expense)
        initCategorySpinner()
        initDateTimePickers()
        resultList = expenseService.getAll(ExpenseSearchCriteria.defaultSearchCriteria())
        calculate()
    }

    private fun initDateTimePickers() {
        initBeginDateTimePicker()
        initEndDateTimePicker()
    }

    private fun initBeginDateTimePicker() {
        mBeginDate = findViewById(R.id.average_begin_dateTimePicker)
        val oneWeekAgo = LocalDateTime.now().minusDays(7)
        mBeginDate.text = oneWeekAgo.toTextForEditable()
        HourChooser(oneWeekAgo, activity, mBeginDate)
    }

    private fun initEndDateTimePicker() {
        mEndDate = findViewById(R.id.average_end_dateTimePicker)
        val now = LocalDateTime.now()
        mEndDate.text = now.toTextForEditable()
        HourChooser(now, activity, mEndDate)
    }

    fun calculateAverageAmount(view: View) {
        calculate()
        Toast.makeText(ApplicationContext.appContext, "Kalkulacja zakończona", Toast.LENGTH_SHORT)
            .show()
    }

    private fun calculate() {
        val categoryName =
            findViewById<Spinner>(R.id.average_category_spinner).selectedItem as String

        val averageAmount = findViewById<TextView>(R.id.averageAmount)
        val averageSearchCriteria =
            AverageSearchCriteria(
                allCategories = categoryName == ALL_CATEGORIES,
                categoryName = if (categoryName == ALL_CATEGORIES) null else categoryName,
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
