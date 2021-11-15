package com.mateuszcholyn.wallet.view.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.util.showIntentMessageIfPresent
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

const val REMOVE_CATEGORY_KEY = "REMOVE_CATEGORY_KEY"

class CategoryActivity : AppCompatActivity(), DIAware {


    override val di by closestDI()
    private val categoryService: CategoryService by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        title = "Kategorie"
        recyclerView = findViewById(R.id.categories_recycler_view)
        viewManager = LinearLayoutManager(this)
        viewAdapter =
            CategoryAdapter(this, this, categoryService, categoryService.getAllOrderByUsageDesc())

        recyclerView = recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        intent.showIntentMessageIfPresent(REMOVE_CATEGORY_KEY)
    }

    fun addCategory(view: View) {
        categoryService.add(
            Category(name = findViewById<EditText>(R.id.newCategoryName).text.toString())
        )
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

}
