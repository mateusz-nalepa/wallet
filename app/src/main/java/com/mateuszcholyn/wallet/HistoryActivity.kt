package com.mateuszcholyn.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mateuszcholyn.wallet.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.histories_recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = HistoryAdapter(arrayOf(
                "Historia",
                "Arya",
                "Tyrion",
                "Jon",
                "Hodor", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"
        ))

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
