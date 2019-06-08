package com.mateuszcholyn.wallet.expense.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.category.service.CategoryService
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.expense.service.ExpenseService
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
        setContentView(R.layout.add_expense_activity)
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
        mExpenseAmount.text = expenseDto.amount.toString().toEditable()
        mCategory.setSelection(categories.indexOf(expenseDto.category))
        date.text = dateAsString(expenseDto.date).toEditable()
        mDescription.text = expenseDto.description.toEditable()
    }

    private fun initDateTimePicker() {
        date = findViewById(R.id.dateTimePicker)
        date.text = simpleDateFormat.format(GregorianCalendar().time)
        HourChooser(mCalendar, activity, date)
    }

    fun addExpense(view: View) {

        if (validateCorrect()) {
            Toast.makeText(applicationContext, "Kwota nie może być pusta!", Toast.LENGTH_SHORT).show()
            return
        }
        val category = mCategory.selectedItem as String
        val expenseAmount = mExpenseAmount.text.toString().toDouble()
        val description = mDescription.text.toString()
        val date = dateAsGregorianCalendar(date)

        ExpenseDto(
                amount = expenseAmount,
                category = category,
                date = date,
                active = true,
                description = description
        ).let {
            expenseService.addExpense(it)
        }

        val intent = Intent(this, ExpenseHistoryActivity::class.java).apply {
            putExtra(SUCCESSFUL_ADD, "Pomyślnie dodano nowy wydatek")
        }
        startActivity(intent)
    }

    fun editExpense(view: View) {
        if (validateCorrect()) {
            Toast.makeText(applicationContext, "Kwota nie może być pusta!", Toast.LENGTH_SHORT).show()
            return
        }
        val id = id.toString().toLong()
        val category = findViewById<Spinner>(R.id.category_spinner).selectedItem as String
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString().toDouble()
        val description = findViewById<EditText>(R.id.description).text.toString()
        val date = dateAsGregorianCalendar(date)

        ExpenseDto(
                id = id,
                amount = expenseAmount,
                category = category,
                date = date,
                active = true,
                description = description
        ).let {
            expenseService.updateExpense(it)
        }

        val intent = Intent(this, ExpenseHistoryActivity::class.java).apply {
            putExtra(SUCCESSFUL_EDIT, "Pomyślnie zaktualizowano wydatek")
        }
        startActivity(intent)
    }

    private fun validateCorrect(): Boolean {
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString()
        return expenseAmount == ""
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}
