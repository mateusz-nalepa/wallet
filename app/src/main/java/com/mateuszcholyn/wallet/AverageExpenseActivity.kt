package com.mateuszcholyn.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mateuszcholyn.wallet.adapter.AverageExpenseAdapter
import com.mateuszcholyn.wallet.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.util.Tablica

class AverageExpenseActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_average_expense)

        recyclerView = findViewById(R.id.average_expense_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = AverageExpenseAdapter(Tablica.napisy())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
