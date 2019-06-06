package com.mateuszcholyn.wallet.expense.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.util.dateAsString


class ExpenseHistoryAdapter(private val myDataset: List<ExpenseDto>) :
        RecyclerView.Adapter<ExpenseHistoryAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName = view.findViewById(R.id.history_category_name) as TextView
        val expenseValue = view.findViewById(R.id.history_expense_value) as TextView
        val date = view.findViewById(R.id.history_date) as TextView
        val description = view.findViewById(R.id.history_description) as TextView
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_single_expense, parent, false)
        return MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expenseDto = myDataset[position]
        holder.categoryName.text = expenseDto.category
        holder.expenseValue.text = expenseDto.amount.toString()
        holder.date.text = dateAsString(expenseDto.date)
        holder.description.text = if (expenseDto.description == "") "brak opisu" else expenseDto.description
    }

    override fun getItemCount() = myDataset.size
}
