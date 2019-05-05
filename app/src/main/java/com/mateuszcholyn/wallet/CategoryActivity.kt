package com.mateuszcholyn.wallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById<RecyclerView>(R.id.categories_recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(arrayOf(
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

//                     use this setting to improve performance if you know that changes
//                     in content do not change the layout size of the RecyclerView
//                    setHasFixedSize(true)
