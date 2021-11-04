package com.mateuszcholyn.wallet.view.category

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService

class CategoryAdapter(
    private val context: Context,
    private val activity: Activity,
    private val categoryService: CategoryService,
    private val categories: List<Category>
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName = view.findViewById(R.id.cat_category_name) as TextView
        val removeButton = view.findViewById(R.id.category_hard_remove) as ImageView
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_category, parent, false)
        return MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoryName = categories[position].name
        holder.categoryName.text = categoryName
        holder.removeButton.setOnClickListener { removeCategory(categoryName) }
    }

    private fun removeCategory(categoryName: String) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Usunięcie kategorii")
            .setMessage("Jesteś pewny że chcesz usunąć kategorię? Spowoduje to również usunięcie wszystkich wydatków w danej kategorii!!")
            .setPositiveButton("Tak") { dialog, which -> removePositiveAction(categoryName) }
            .setNegativeButton("Nie", null)
            .show()
    }

    private fun removePositiveAction(categoryName: String) {
        if (categoryService.remove(categoryName)) {
            val intent = Intent(context, CategoryActivity::class.java).apply {
                putExtra(REMOVE_CATEGORY_KEY, "Pomyślnie usunięto kategorię ${categoryName}")
            }
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = categories.size
}
