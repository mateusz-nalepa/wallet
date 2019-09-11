package com.mateuszcholyn.wallet.domain.category.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.activity.CategoryActivity
import com.mateuszcholyn.wallet.domain.category.activity.REMOVE_CATEGORY_KEY
import com.mateuszcholyn.wallet.domain.category.model.CategoryDto
import com.mateuszcholyn.wallet.domain.category.service.CategoryService

class CategoryAdapter(
        private val context: Context,
        private val activity: Activity,
        private val categoryService: CategoryService,
        private val categories: List<CategoryDto>
) :
        RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName = view.findViewById(R.id.cat_category_name) as TextView
        val removeButton = view.findViewById(R.id.category_hard_remove) as ImageView
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_category, parent, false)
        return CategoryAdapter.MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoryName = categories[position].name
        holder.categoryName.text = categoryName
        holder.removeButton.setOnClickListener { hardRemoveCategory(categoryName) }
    }

    private fun hardRemoveCategory(categoryName: String) {
        AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Usunięcie kategorii")
                .setMessage("Jesteś pewny że chcesz usunąć kategorię? Spowoduje to również usunięcie wszystkich wydatków w danej kategorii!!")
                .setPositiveButton("Tak") { dialog, which -> removePositiveAction(categoryName) }
                .setNegativeButton("Nie", null)
                .show()
    }

    private fun removePositiveAction(categoryName: String) {
        if (categoryService.hardRemove(categoryName)) {
            val intent = Intent(context, CategoryActivity::class.java).apply {
                putExtra(REMOVE_CATEGORY_KEY, "Pomyślnie usunięto kategorię ${categoryName}")
            }
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = categories.size
}
