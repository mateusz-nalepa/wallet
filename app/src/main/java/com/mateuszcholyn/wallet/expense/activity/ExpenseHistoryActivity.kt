package com.mateuszcholyn.wallet.expense.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.expense.adapter.ExpenseHistoryAdapter
import com.mateuszcholyn.wallet.expense.model.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.expense.service.ExpenseService

class ExpenseHistoryActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val expenseService: ExpenseService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.histories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = ExpenseHistoryAdapter(expenseService.getAll(ExpenseSearchCriteria()))

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun showOrHideDetails(view: View) {
        
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
