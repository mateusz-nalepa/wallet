package com.mateuszcholyn.wallet.ui.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mateuszcholyn.wallet.databinding.FragmentAddExpenseBinding
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.oneWeekAgo
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.time.LocalDateTime

class AddExpenseFragment : Fragment(), DIAware {

    override val di: DI by closestDI()
    private val expenseService: ExpenseService by instance()

    private lateinit var addExpenseViewModel: AddExpenseViewModel
    private var _binding: FragmentAddExpenseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val result =
            expenseService.averageExpense(
                ExpenseSearchCriteria(
                    allCategories = true,
                    beginDate = oneWeekAgo(),
                    endDate = LocalDateTime.now()
                )
            )

        val wCiaguOstatniegoTygodnia = """
            XD W ciagu ostatnich 7 dni wydales: ${result.wholeAmount.asPrinteableAmount()} zł, 
            czyli srednio na dzien: ${result.averageAmount.asPrinteableAmount()} zł
        """.trimIndent()


        addExpenseViewModel =
            ViewModelProvider(this).get(AddExpenseViewModel::class.java)

        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        addExpenseViewModel.text.observe(viewLifecycleOwner, {
            textView.text = wCiaguOstatniegoTygodnia
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}