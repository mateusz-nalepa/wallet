package com.mateuszcholyn.wallet.ui.message

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.databinding.RecyclerviewSingleExpenseBinding
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.view.showShortText

data class SummaryAdapterModel(
    val id: Long,
    val description: String,
    val date: String,
    val expenseValue: String,
    val categoryName: String,
)

class SummaryAdapter(
    val expenseService: ExpenseService,
    private val expenses: List<SummaryAdapterModel>,
    private val refreshScreenFunction: () -> Unit,

    ) :
    RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    lateinit var context: Context

    class SummaryViewHolder(val recyclerviewSingleExpenseBinding: RecyclerviewSingleExpenseBinding) :
        RecyclerView.ViewHolder(recyclerviewSingleExpenseBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryViewHolder {
        context = parent.context
        return SummaryViewHolder(
            RecyclerviewSingleExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val expense = expenses[position]

        holder.recyclerviewSingleExpenseBinding.viewModel = expense
        holder.recyclerviewSingleExpenseBinding.deleteExpense.setOnClickListener {
            hardRemoveExpense(
                expense.id
            )
        }
    }

    override fun getItemCount() = expenses.size

    private fun hardRemoveExpense(expenseId: Long) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Usunięcie wydatku")
            .setMessage("Jesteś pewny że chcesz usunąć wybrany wydatek?")
            .setPositiveButton("Tak") { dialog, which -> removePositiveAction(expenseId) }
            .setNegativeButton("Nie", null)
            .show()
    }

    private fun removePositiveAction(expenseId: Long) {
        if (expenseService.hardRemove(expenseId)) {
            showShortText("removeExpense $expenseId")
            refreshScreenFunction.invoke()
        }
    }

}

