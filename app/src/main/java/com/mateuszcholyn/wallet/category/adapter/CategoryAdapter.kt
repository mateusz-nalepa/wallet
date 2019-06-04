package com.mateuszcholyn.wallet.category.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.category.model.CategoryDto


class CategoryAdapter(private val categories: List<CategoryDto>) :
        RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.txtTitle)!!
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false)
        return MyViewHolder(v as TextView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = categories[position].name
    }

    override fun getItemCount() = categories.size
}
