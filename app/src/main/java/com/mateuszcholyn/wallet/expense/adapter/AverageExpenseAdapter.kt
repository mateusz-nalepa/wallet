package com.mateuszcholyn.wallet.expense.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mateuszcholyn.wallet.R


class AverageExpenseAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<AverageExpenseAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leftText = view.findViewById(R.id.leftText) as TextView
        val rightText = view.findViewById(R.id.rightText) as TextView

    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.average_single_category, parent, false)
        return MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.leftText.text = myDataset[position]
        holder.rightText.text = myDataset[position]
    }

    override fun getItemCount() = myDataset.size
}
