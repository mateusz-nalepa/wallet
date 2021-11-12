package com.mateuszcholyn.wallet.view.expense

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
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
import java.time.LocalDateTime


const val REMOVE_EXPENSE_KEY = "REMOVE_EXPENSE_KEY"
const val EDIT_EXPENSE = "EDIT_EXPENSE"
const val SEARCHED = "SEARCHED"


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

        resultList = handleSearchResults()
        initDateTimePickers(resultList.isEmpty())
        initCategorySpinner()
        initQuickRangeSpinner()

        viewAdapter = ExpenseHistoryAdapter(this, this, expenseService, resultList)
        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        showIntentMessage()
    }

    private fun handleResultsWhenNoExpenseIsAdded(): List<Expense> {

        val expenses = expenseService.getAll(ExpenseSearchCriteria.defaultSearchCriteria())

        if (expenses.isEmpty()) {
            Toast.makeText(
                ApplicationContext.appContext,
                "Brak wydatków w bazie, dodaj jakiś wydatek",
                Toast.LENGTH_SHORT
            ).show()
        }

        return expenses
    }

    private fun handleSearchResults(): List<Expense> {
        val expenseSearchCriteria =
            intent.getSerializableExtra(SEARCHED)
                ?: return handleResultsWhenNoExpenseIsAdded()

        val resultXD = expenseService.getAll(expenseSearchCriteria as ExpenseSearchCriteria)

        if (resultXD.isEmpty()) {
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

        return resultXD
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


    private fun initDateTimePickers(isEmptyResultSize: Boolean) {
        initBeginDateTimePicker(isEmptyResultSize)
        initEndDateTimePicker(isEmptyResultSize)
    }

    private fun initBeginDateTimePicker(isEmptyResultSize: Boolean) {
        mBeginDate = findViewById(R.id.history_begin_dateTimePicker)
        var currentTime = currentDateAsString()
        if (!isEmptyResultSize) {
            currentTime = findEarliest(resultList).toHumanText()
        }

        mBeginDate.text = currentTime
        DateTimeChooser(LocalDateTime.now(), activity, mBeginDate)
    }

    private fun initEndDateTimePicker(isEmptyResultSize: Boolean) {
        mEndDate = findViewById(R.id.history_end_dateTimePicker)
        var currentTime = currentDateAsString()
        if (!isEmptyResultSize) {
            currentTime = findLatest(resultList).toHumanText()
        }

        mEndDate.text = currentTime
        DateTimeChooser(LocalDateTime.now(), activity, mEndDate)
    }

    private fun showIntentMessage() {
        intent.showIntentMessageIfPresent(REMOVE_EXPENSE_KEY)
        intent.showIntentMessageIfPresent(SUCCESSFUL_ADD)
        intent.showIntentMessageIfPresent(SUCCESSFUL_EDIT)
    }

    fun showHistoryResults(view: View) {
        val category = findViewById<Spinner>(R.id.history_category_spinner).selectedItem as String
        val expenseSearchCriteria = prepareExpenseSearchCriteria(category, mBeginDate, mEndDate)

        val intent =
            Intent(this, ExpenseHistoryActivity::class.java).apply {
                putExtra(SEARCHED, expenseSearchCriteria)
            }
        startActivity(intent)
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


class QuickRangeSelectedListener(
    private var mBeginDate: TextView,
    private var mEndDate: TextView,
) : OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        QuickRange.modifyBasedOnPosition(position, mBeginDate, mEndDate)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


object QuickRange {

    private val quickRanges = mapOf(
        0 to "Ostatni tydzień",
        1 to "Ostatni Miesiąc",
    )

    fun quickRangesNames(): List<String> =
        quickRanges.values.toList()

    fun modifyBasedOnPosition(position: Int, mBeginDate: TextView, mEndDate: TextView) {
        when (position) {
            0 -> {
                mBeginDate.text = LocalDateTime.now().minusDays(7).toHumanText()
                mEndDate.text = currentDateAsString()
            }
            1 -> {
                mBeginDate.text = LocalDateTime.now().minusMonths(1).toHumanText()
                mEndDate.text = currentDateAsString()
            }
        }
    }

}