package com.mateuszcholyn.wallet.view.expense

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
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
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.DateTimeChooser
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.oneWeekAgo
import com.mateuszcholyn.wallet.view.QuickRange
import com.mateuszcholyn.wallet.view.QuickRangeSelectedListener
import com.mateuszcholyn.wallet.view.initSpinner
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
        initDateTimePickers()
        initSpinners()
        resultList = expenseService.getAll(ExpenseSearchCriteria.defaultSearchCriteria())
        calculate()
    }

    private fun initDateTimePickers() {
        mBeginDate = findViewById(R.id.average_beginDateTimePicker)
        mEndDate = findViewById(R.id.average_endDateTimePicker)

        DateTimeChooser(oneWeekAgo(), this, mBeginDate)
        DateTimeChooser(LocalDateTime.now(), this, mEndDate)

        QuickRange.setDefaultDates(mBeginDate, mEndDate)
    }

    fun calculateAverageAmount(view: View) {
        calculate()
        Toast.makeText(ApplicationContext.appContext, "Kalkulacja zakończona", Toast.LENGTH_SHORT)
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        val categoryName =
            findViewById<Spinner>(R.id.average_category_spinner).selectedItem as String

        val averageAmount = findViewById<TextView>(R.id.averageAmount)
        val expenseSearchCriteria = prepareExpenseSearchCriteria(categoryName, mBeginDate, mEndDate)
        averageAmount.text =
            "${expenseService.averageExpense(expenseSearchCriteria).asPrinteableAmount()} zł"
    }

    private fun initSpinners() {
        initCategorySpinner()
        initQuickRangeSpinner()
    }

    private fun initCategorySpinner() {
        val allCategories = listOf(ALL_CATEGORIES) + categoryService.getAllNamesOnly()
        initSpinner(R.id.average_category_spinner, allCategories)
    }

    private fun initQuickRangeSpinner() {
        val spinner = initSpinner(R.id.average_quick_range_spinner, QuickRange.quickRangesNames())
        spinner.onItemSelectedListener = QuickRangeSelectedListener(mBeginDate, mEndDate)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
