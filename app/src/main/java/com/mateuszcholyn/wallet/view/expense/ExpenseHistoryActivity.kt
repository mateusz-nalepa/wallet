package com.mateuszcholyn.wallet.view.expense

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.onEmpty
import com.mateuszcholyn.wallet.onNotEmpty
import com.mateuszcholyn.wallet.util.*
import com.mateuszcholyn.wallet.view.QuickRange
import com.mateuszcholyn.wallet.view.QuickRangeSelectedListener
import com.mateuszcholyn.wallet.view.initSpinner
import com.mateuszcholyn.wallet.view.showShortText
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

    private fun updateListOnScreen(newResultList: List<Expense>) {
        resultList = newResultList
        viewAdapter = ExpenseHistoryAdapter(this, this, expenseService, resultList)
        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun handleResultsWhenOpeningActivity() {
        ExpenseSearchCriteria
            .defaultSearchCriteria(mBeginDate.toLocalDateTime(), mEndDate.toLocalDateTime())
            .let { expenseService.getAll(it) }
            .onEmpty { showShortText("Brak wydatków w bazie, dodaj jakiś wydatek") }
            .also { updateListOnScreen(it) }
    }

    private fun initCategorySpinner() {
        val allCategories = listOf(ALL_CATEGORIES) + categoryService.getAllNamesOnly()
        initSpinner(R.id.history_category_spinner, allCategories)
    }

    private fun initQuickRangeSpinner() {
        val spinner = initSpinner(R.id.history_quick_range_spinner, QuickRange.quickRangesNames())
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
        expenseService.getAll(expenseSearchCriteria)
            .onEmpty { showShortText("Brak wydatków dla podanych kryteriów") }
            .onNotEmpty { showShortText("Wyniki dla podanych kryteriów") }
            .also { updateListOnScreen(it) }
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
