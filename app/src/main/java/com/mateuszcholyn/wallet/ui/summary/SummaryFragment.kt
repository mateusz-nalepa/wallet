package com.mateuszcholyn.wallet.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mateuszcholyn.wallet.databinding.FragmentSummaryV2Binding
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.fragmentViewModel
import com.mateuszcholyn.wallet.util.DateTimeChooserV2
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.view.QuickRangeSelectedListenerV2
import com.mateuszcholyn.wallet.view.QuickRangeV2
import kotlinx.android.synthetic.main.fragment_summary_v2.*
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import java.time.LocalDateTime

class SummaryFragment(
    private val showAddExpenseFragmentFunction: () -> Unit,
    private val showEditExpenseFragmentFunction: (Expense) -> Unit,
) : Fragment(), DIAware {

    override val di by closestDI()
    private val summaryViewModel: SummaryViewModel by fragmentViewModel()

    private var bindingNullable: FragmentSummaryV2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentSummaryV2Binding.inflate(inflater, container, false)
        binding.viewModel = summaryViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        initCategorySpinner()
        initDateTimePickers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summaryViewModel.showAddExpenseFragmentFunction = showAddExpenseFragmentFunction

        binding.viewModel!!.expenses.observe(viewLifecycleOwner, { newExpenses ->
            summary_recycler_view.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = SummaryAdapter(
                    expenseService = binding.viewModel!!.expenseService,
                    expenses = newExpenses,
                    refreshScreenFunction =  { binding.viewModel!!.showSummary() },
                    showEditExpenseFragmentFunction = showEditExpenseFragmentFunction,
                )
            }
        })
    }

    private fun initCategorySpinner() {
        binding.summaryCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    summaryViewModel.actualCategoryPosition = position
                }
            }
    }

    private fun initDateTimePickers() {
        DateTimeChooserV2(
            LocalDateTime.now().atStartOfTheDay(),
            requireContext(),
            summaryViewModel.beginDate,
            binding.summaryBeginDateTimePicker
        )

        DateTimeChooserV2(
            LocalDateTime.now(),
            requireContext(),
            summaryViewModel.endDate,
            binding.summaryEndDateTimePicker
        )

        binding.summaryQuickRangeSpinner.onItemSelectedListener =
            QuickRangeSelectedListenerV2(
                summaryViewModel.beginDate,
                summaryViewModel.endDate
            )

        QuickRangeV2.setDefaultDates(summaryViewModel.beginDate, summaryViewModel.endDate)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}