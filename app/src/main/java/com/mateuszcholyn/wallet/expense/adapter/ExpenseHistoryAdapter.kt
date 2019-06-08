package com.mateuszcholyn.wallet.expense.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.expense.activity.ExpenseHistoryActivity
import com.mateuszcholyn.wallet.expense.activity.REMOVE_EXPENSE_KEY
import com.mateuszcholyn.wallet.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.asShortCategoryName
import com.mateuszcholyn.wallet.util.dateAsString


class ExpenseHistoryAdapter(
        private val context: Context,
        private val activity: Activity,
        private val expenseService: ExpenseService,
        private val myDataset: List<ExpenseDto>
) : RecyclerView.Adapter<ExpenseHistoryAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shortCategoryName = view.findViewById(R.id.history_short_category_name) as TextView
        val fullCategoryName = view.findViewById(R.id.history_full_category_name) as TextView
        val expenseValue = view.findViewById(R.id.history_expense_value) as TextView
        val date = view.findViewById(R.id.history_date) as TextView
        val description = view.findViewById(R.id.history_description) as TextView
        val showOrHideButton = view.findViewById(R.id.history_show_or_hide) as ImageView
        val detailsLayout = view.findViewById(R.id.history_details) as LinearLayout
        val editButton = view.findViewById(R.id.edit_expense) as Button
        val removeButton = view.findViewById(R.id.delete_expense) as Button

    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_single_expense, parent, false)
        return MyViewHolder(v as View)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expenseDto = myDataset[position]
        val category = expenseDto.category
        holder.shortCategoryName.text = category.asShortCategoryName()
        holder.fullCategoryName.text = category
        holder.expenseValue.text = expenseDto.amount.toString()
        holder.date.text = dateAsString(expenseDto.date)
        holder.description.text = if (expenseDto.description == "") "brak opisu" else expenseDto.description
        holder.detailsLayout.visibility = View.GONE
        holder.showOrHideButton.setOnClickListener { showOrHideListener(holder.detailsLayout) }
        holder.removeButton.setOnClickListener { hardRemoveCategory(expenseDto.id) }

    }

    private fun showOrHideListener(detailsLayout: LinearLayout) {
        if (detailsLayout.visibility == View.GONE) {
            detailsLayout.visibility = View.VISIBLE
        } else {
            detailsLayout.visibility = View.GONE
        }
    }

    private fun hardRemoveCategory(expenseId: Long) {
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
            val intent = Intent(context, ExpenseHistoryActivity::class.java).apply {
                putExtra(REMOVE_EXPENSE_KEY, "Pomyślnie usunięto wydatek")
            }
            activity.startActivity(intent)
        }
    }


    override fun getItemCount() = myDataset.size
}
