package com.mateuszcholyn.wallet.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.databinding.RecyclerviewSingleCategoryBinding
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.view.showShortText

data class SingleCategoryV2Model(
    val name: String,
) {

    fun removeCategory() {
        showShortText("NOT IMPLEMENTED YET")
    }

}

class CategoryAdapterV2(
    val categoryService: CategoryService,
    private val categories: List<SingleCategoryV2Model>
) :
    RecyclerView.Adapter<CategoryAdapterV2.MyViewHolderV2>() {

    class MyViewHolderV2(val recyclerviewSingleCategoryBinding: RecyclerviewSingleCategoryBinding) :
        RecyclerView.ViewHolder(recyclerviewSingleCategoryBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolderV2 {
        return MyViewHolderV2(
            RecyclerviewSingleCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolderV2, position: Int) {
        holder.recyclerviewSingleCategoryBinding.viewModel = categories[position]
    }

    override fun getItemCount() = categories.size
}
