package com.mateuszcholyn.wallet.domain.expense.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.R.layout
import com.mateuszcholyn.wallet.databinding.MenuButtonBinding
import com.mateuszcholyn.wallet.domain.category.service.CategoryService
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.domain.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.*
import java.util.*


const val SUCCESSFUL_ADD = "SUCCESSFUL_ADD"
const val SUCCESSFUL_EDIT = "SUCCESSFUL_EDIT"

class AddExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private var mCalendar: Calendar = Calendar.getInstance()
    private lateinit var activity: Activity
    private lateinit var date: TextView
    private lateinit var id: TextView
    private lateinit var addLayout: LinearLayout
    private lateinit var editLayout: LinearLayout
    private lateinit var mCategory: Spinner
    private lateinit var mExpenseAmount: EditText
    private lateinit var mDescription: EditText
    private lateinit var categories: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MenuButtonBinding>(this, layout.add_expense_activity)
        initializeInjector()
        title = "Dodaj wydatek"
        activity = this
        initCategorySpinner()
        initDateTimePicker()
        initActivity()
    }

    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.category_spinner)
        categories = categoryService.getAllNamesOnly()

        ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categories
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun initActivity() {
        mCategory = findViewById(R.id.category_spinner)
        mExpenseAmount = findViewById(R.id.expenseAmount)
        mDescription = findViewById(R.id.description)

        mDescription.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(exampleView: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO || event != null && event.keyCode == KEYCODE_ENTER) {
                    date.performClick()
                    return true
                } else {
                    return false
                }
            }
        })

        editLayout = findViewById(R.id.layout_edit_expense)
        editLayout.visibility = View.GONE

        addLayout = findViewById(R.id.layout_add_expense)
        addLayout.visibility = View.GONE

        id = findViewById(R.id.expense_id)

        val expenseDto = intent.getSerializableExtra(EDIT_EXPENSE)
        if (expenseDto != null && expenseDto is ExpenseDto) {
            initEditExpense(expenseDto)

        } else {
            addLayout.visibility = View.VISIBLE
            title = "Dodaj wydatek"
        }
    }

    private fun initEditExpense(expenseDto: ExpenseDto) {
        title = "Edytuj wydatek"
        editLayout.visibility = View.VISIBLE

        id.text = expenseDto.id.toString()
        mExpenseAmount.text = expenseDto.amount.toEditable()
        mCategory.setSelection(categories.indexOf(expenseDto.category.name))
        date.text = expenseDto.date.toEditable()
        mDescription.text = expenseDto.description.toEditable()
    }

    private fun initDateTimePicker() {
        date = findViewById(R.id.dateTimePicker)
        date.text = currentCalendarAsString()
        HourChooser(mCalendar, activity, date)
    }

    fun addExpense(view: View) {

        if (validationIncorrect()) {
            Toast.makeText(applicationContext, "Kwota jest niepoprawna!", Toast.LENGTH_SHORT).show()
            return
        }

        ExpenseDto(
                amount = mExpenseAmount.toDouble(),
                category = categoryService.getByName(mCategory.selectedItemAsString()),
                date = date.toLocalDateTime(),
                description = mDescription.textToString()
        ).let {
            expenseService.addExpense(it)
        }

        val intent = Intent(this, ExpenseHistoryActivity::class.java).apply {
            putExtra(SUCCESSFUL_ADD, "Pomyślnie dodano nowy wydatek")
        }
        startActivity(intent)
    }

    fun editExpense(view: View) {
        if (validationIncorrect()) {
            Toast.makeText(applicationContext, "Kwota jest niepoprawna!", Toast.LENGTH_SHORT).show()
            return
        }

        ExpenseDto(
                id = id.toLong(),
                amount = mExpenseAmount.toDouble(),
                category = categoryService.getByName(mCategory.selectedItem as String),
                date = date.toLocalDateTime(),
                description = mDescription.textToString()
        ).let {
            expenseService.updateExpense(it)
        }

        val intent = Intent(this, ExpenseHistoryActivity::class.java).apply {
            putExtra(SUCCESSFUL_EDIT, "Pomyślnie zaktualizowano wydatek")
        }
        startActivity(intent)
    }

    private fun validationIncorrect(): Boolean {
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString()
        return expenseAmount == "" || expenseAmount.startsWith(".") || expenseAmount.startsWith("-")
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}
