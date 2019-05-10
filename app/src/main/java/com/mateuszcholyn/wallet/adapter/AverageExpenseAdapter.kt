package com.mateuszcholyn.wallet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mateuszcholyn.wallet.R


class AverageExpenseAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<AverageExpenseAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName = view.findViewById(R.id.history_category_name) as TextView
        val expenseValue = view.findViewById(R.id.history_expense_value) as TextView

    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.history_single_expense, parent, false)
        return MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.categoryName.text = myDataset[position]
        holder.expenseValue.text = myDataset[position]
    }

    override fun getItemCount() = myDataset.size
}
