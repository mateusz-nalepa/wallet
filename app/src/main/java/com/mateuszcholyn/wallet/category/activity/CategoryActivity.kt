package com.mateuszcholyn.wallet.category.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.category.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.category.model.CategoryDto
import com.mateuszcholyn.wallet.category.service.CategoryService


class CategoryActivity : AppCompatActivity(), AppCompatActivityInjector {


    override val injector: KodeinInjector = KodeinInjector()
    private val categoryService: CategoryService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        GlobalExceptionHandler(this)
        initializeInjector()

        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.categories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(categoryService.getAll())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun addCategory(view: View) {
        categoryService.addNewCategory(
                CategoryDto(
                        name = findViewById<EditText>(R.id.newCategoryName).text.toString()
                )
        )

        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

}
