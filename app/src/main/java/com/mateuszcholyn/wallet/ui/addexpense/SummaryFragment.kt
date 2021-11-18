package com.mateuszcholyn.wallet.ui.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mateuszcholyn.wallet.databinding.FragmentSummaryBinding
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.oneWeekAgo
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.time.LocalDateTime

class SummaryFragment : Fragment(), DIAware {

    override val di: DI by closestDI()
    private val expenseService: ExpenseService by instance()

    private lateinit var summaryViewModel: SummaryViewModel

    private var bindingNullable: FragmentSummaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentSummaryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summaryViewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
        observeViewModel()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.summaryButton.setOnClickListener { summaryViewModel.textSummaryLiveData.postValue(getText()) }
    }

    private fun observeViewModel() {
        summaryViewModel.textSummaryLiveData.observe(viewLifecycleOwner, { newText ->
            val textView: TextView = binding.summaryText
            textView.text = newText
        })
    }

    private fun getText(): String {
        val result =
            expenseService.averageExpense(
                ExpenseSearchCriteria(
                    allCategories = true,
                    beginDate = oneWeekAgo(),
                    endDate = LocalDateTime.now()
                )
            )

        return """
            XD W ciagu ostatnich 7 dni wydales: ${result.wholeAmount.asPrinteableAmount()} zł, 
            czyli srednio na dzien: ${result.averageAmount.asPrinteableAmount()} zł
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }
}