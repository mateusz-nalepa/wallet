package com.mateuszcholyn.wallet.category.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.category.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.category.model.CategoryDto
import com.mateuszcholyn.wallet.category.service.CategoryService
import com.mateuszcholyn.wallet.config.ApplicationContext

const val REMOVE_CATEGORY_KEY = "REMOVE_CATEGORY_KEY"

class CategoryActivity : AppCompatActivity(), AppCompatActivityInjector {


    override val injector: KodeinInjector = KodeinInjector()
    private val categoryService: CategoryService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.categories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(this, this, categoryService, categoryService.getAll())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        showIntentMessage()
    }

    private fun showIntentMessage() {
        val stringExtra = intent.getStringExtra(REMOVE_CATEGORY_KEY)

        if (stringExtra != null) {
            Toast.makeText(ApplicationContext.appContext, stringExtra, Toast.LENGTH_SHORT).show()
        }
    }

    fun addCategory(view: View) {
        categoryService.addNewCategory(
                CategoryDto(name = findViewById<EditText>(com.mateuszcholyn.wallet.R.id.newCategoryName).text.toString())
        )
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

}
