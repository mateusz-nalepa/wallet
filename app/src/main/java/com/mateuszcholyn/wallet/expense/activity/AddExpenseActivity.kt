package com.mateuszcholyn.wallet.expense.activity

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
import com.mateuszcholyn.wallet.category.service.CategoryService
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.HourChooser
import com.mateuszcholyn.wallet.util.dateAsGregorianCalendar
import com.mateuszcholyn.wallet.util.simpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()
    private val categoryService: CategoryService by instance()

    private var mCalendar: Calendar = Calendar.getInstance()
    private lateinit var activity: Activity
    private lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_activity)
        initializeInjector()
        title = "Dodaj wydatek"
        activity = this
        initCategorySpinner()
        initDateTimePicker()
    }

    private fun initDateTimePicker() {
        date = findViewById(R.id.dateTimePicker)
        date.text = simpleDateFormat.format(GregorianCalendar().time)
        HourChooser(mCalendar, activity, date)
    }

    private fun initCategorySpinner() {

        val spinner: Spinner = findViewById(R.id.category_spinner)
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

    fun addExpense(view: View) {

        if (validateCorrect()) {
            Toast.makeText(applicationContext, "Kwota nie może być pusta!", Toast.LENGTH_SHORT).show()
            return
        }
        val category = findViewById<Spinner>(R.id.category_spinner).selectedItem as String
        val expenseAmount = findViewById<EditText>(R.id.expenseAmount).text.toString().toDouble()
        val description = findViewById<EditText>(R.id.description).text.toString()
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

        val intent = Intent(this, ExpenseHistoryActivity::class.java)
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
