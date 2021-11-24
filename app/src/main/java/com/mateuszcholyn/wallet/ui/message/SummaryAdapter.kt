package com.mateuszcholyn.wallet.ui.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.databinding.RecyclerviewSingleExpenseBinding
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.view.showShortText

data class SummaryAdapterModel(
    val description: String,
    val date: String,
    val expenseValue: String,
    val categoryName: String,
) {

    fun editExpense() {
        showShortText("editExpense")
    }

    fun removeExpense() {
        showShortText("removeExpense")
    }

}

class SummaryAdapter(
    val expenseService: ExpenseService,
    private val expenses: List<SummaryAdapterModel>
) :
    RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    class SummaryViewHolder(val recyclerviewSingleExpenseBinding: RecyclerviewSingleExpenseBinding) :
        RecyclerView.ViewHolder(recyclerviewSingleExpenseBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryViewHolder {
        return SummaryViewHolder(
            RecyclerviewSingleExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.recyclerviewSingleExpenseBinding.viewModel = expenses[position]
    }

    override fun getItemCount() = expenses.size
}

