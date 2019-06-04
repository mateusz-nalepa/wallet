package com.mateuszcholyn.wallet.expense.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.expense.adapter.AverageExpenseAdapter
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.Tablica

class AverageExpenseActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_average_expense)

        recyclerView = findViewById(R.id.average_expense_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = AverageExpenseAdapter(Tablica.napisy())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
