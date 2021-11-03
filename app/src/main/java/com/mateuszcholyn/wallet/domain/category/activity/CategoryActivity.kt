package com.mateuszcholyn.wallet.domain.category.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.adapter.CategoryAdapter
import com.mateuszcholyn.wallet.domain.category.model.CategoryDto
import com.mateuszcholyn.wallet.domain.category.service.CategoryService
import com.mateuszcholyn.wallet.util.showIntentMessageIfPresent

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
        title = "Kategorie"
        recyclerView = findViewById(R.id.categories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(this, this, categoryService, categoryService.getAll())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        intent.showIntentMessageIfPresent(REMOVE_CATEGORY_KEY)
    }

    fun addCategory(view: View) {
        categoryService.add(
                CategoryDto(name = findViewById<EditText>(R.id.newCategoryName).text.toString())
        )
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

}
