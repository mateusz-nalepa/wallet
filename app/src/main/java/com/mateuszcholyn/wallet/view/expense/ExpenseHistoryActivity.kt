package com.mateuszcholyn.wallet.view.expense

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.*
import com.mateuszcholyn.wallet.view.QuickRange
import com.mateuszcholyn.wallet.view.QuickRangeSelectedListener
import java.time.LocalDateTime


const val REMOVE_EXPENSE_KEY = "REMOVE_EXPENSE_KEY"
const val EDIT_EXPENSE = "EDIT_EXPENSE"


class ExpenseHistoryActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var activity: Activity
    private lateinit var mBeginDate: TextView
    private lateinit var mEndDate: TextView
    private lateinit var resultList: List<Expense>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        activity = this
        setContentView(R.layout.activity_history)
        title = "Historia wydatków"
        recyclerView = findViewById(R.id.histories_recycler_view)
        viewManager = LinearLayoutManager(this)

        initDateTimePickers()
        handleResultsWhenOpeningActivity()

        initCategorySpinner()
        initQuickRangeSpinner()

        showIntentMessage()
    }

    private fun updateListOnScreen() {
        viewAdapter = ExpenseHistoryAdapter(this, this, expenseService, resultList)
        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun handleResultsWhenOpeningActivity() {
        ExpenseSearchCriteria
            .defaultSearchCriteria(
                beginDate = mBeginDate.toLocalDateTime(),
                endDate = mEndDate.toLocalDateTime()
            )
            .let { expenseService.getAll(it) }
            .also {
                if (it.isEmpty()) {
                    Toast.makeText(
                        ApplicationContext.appContext,
                        "Brak wydatków w bazie, dodaj jakiś wydatek",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .also { resultList = it }
            .also { updateListOnScreen() }

    }

    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.history_category_spinner)
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

    private fun initQuickRangeSpinner() {
        val spinner: Spinner = findViewById(R.id.history_quick_range_spinner)
        val allQuickRanges = QuickRange.quickRangesNames()

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            allQuickRanges
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = QuickRangeSelectedListener(mBeginDate, mEndDate)
    }


    private fun initDateTimePickers() {
        mBeginDate = findViewById(R.id.history_begin_dateTimePicker)
        mEndDate = findViewById(R.id.history_end_dateTimePicker)

        DateTimeChooser(oneWeekAgo(), activity, mBeginDate)
        DateTimeChooser(LocalDateTime.now(), activity, mEndDate)

        QuickRange.setDefaultDates(mBeginDate, mEndDate)
    }

    private fun showIntentMessage() {
        intent.showIntentMessageIfPresent(REMOVE_EXPENSE_KEY)
        intent.showIntentMessageIfPresent(SUCCESSFUL_ADD)
        intent.showIntentMessageIfPresent(SUCCESSFUL_EDIT)
    }

    fun showHistoryResults(view: View) {
        val category = findViewById<Spinner>(R.id.history_category_spinner).selectedItem as String
        val expenseSearchCriteria = prepareExpenseSearchCriteria(category, mBeginDate, mEndDate)

        handleSearchResults(expenseSearchCriteria)
    }

    private fun handleSearchResults(expenseSearchCriteria: ExpenseSearchCriteria) {
        resultList = expenseService.getAll(expenseSearchCriteria)

        updateListOnScreen()

        if (resultList.isEmpty()) {
            Toast.makeText(
                ApplicationContext.appContext,
                "Brak wydatków dla podanych kryteriów",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                ApplicationContext.appContext,
                "Wyniki dla podanych kryteriów",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun exportHistoryResults(view: View) {
        val category = findViewById<Spinner>(R.id.history_category_spinner).selectedItem as String
        val expenseSearchCriteria = prepareExpenseSearchCriteria(category, mBeginDate, mEndDate)
        saveToFile(applicationContext, this, expenseService.getAll(expenseSearchCriteria))
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
