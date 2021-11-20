package com.mateuszcholyn.wallet.ui.addoreditexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentAddOrEditExpenseBinding
import com.mateuszcholyn.wallet.fragmentViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class AddOrEditExpenseFragment : Fragment(), DIAware {
    override val di: DI by closestDI()
    private val addOrEditExpenseViewModel: AddOrEditExpenseViewModel by fragmentViewModel()

    private var bindingNullable: FragmentAddOrEditExpenseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentAddOrEditExpenseBinding.inflate(inflater, container, false)
        binding.viewModel = addOrEditExpenseViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        initCategorySpinner()
        return binding.root
    }

    private fun initCategorySpinner() {
        binding.addOrEditExpenseCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    addOrEditExpenseViewModel.actualCategoryPosition = position
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }
}