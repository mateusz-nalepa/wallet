package com.mateuszcholyn.wallet.category.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.util.Tablica

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.categories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(Tablica.napisy())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}
