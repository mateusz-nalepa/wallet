package com.mateuszcholyn.wallet.ui.message

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mateuszcholyn.wallet.databinding.RecyclerviewSingleExpenseBinding
import com.mateuszcholyn.wallet.domain.expense.Expense
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
    private val showEditExpenseFragmentFunction: (Expense) -> Unit = {}

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
        holder.recyclerviewSingleExpenseBinding.editExpense.setOnClickListener {
            openEditExpenseFragment(expense.id)
        }
        holder.recyclerviewSingleExpenseBinding.deleteExpense.setOnClickListener {
            hardRemoveExpense(expense.id)
        }
        holder.recyclerviewSingleExpenseBinding.summaryExpenseShortDetails.setOnClickListener {
            showOrHideListener(holder.recyclerviewSingleExpenseBinding.summaryExpenseFullDetails)
        }

        holder.recyclerviewSingleExpenseBinding.summaryExpenseFullDetails.visibility = View.GONE
        holder.recyclerviewSingleExpenseBinding.summaryExpenseFullDetails.setOnClickListener {
            showOrHideListener(holder.recyclerviewSingleExpenseBinding.summaryExpenseFullDetails)
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

    private fun openEditExpenseFragment(expenseId: Long) {
        showEditExpenseFragmentFunction.invoke(expenseService.getById(expenseId))
    }

    private fun showOrHideListener(detailsLayout: LinearLayout) {
        if (detailsLayout.visibility == View.GONE) {
            detailsLayout.visibility = View.VISIBLE
        } else {
            detailsLayout.visibility = View.GONE
        }
    }

}

