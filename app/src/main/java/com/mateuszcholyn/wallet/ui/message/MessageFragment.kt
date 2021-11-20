package com.mateuszcholyn.wallet.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentMessageBinding
import com.mateuszcholyn.wallet.fragmentViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class MessageFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val messageViewModel: MessageViewModel by fragmentViewModel()

    private var bindingNullable: FragmentMessageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentMessageBinding.inflate(inflater, container, false)
        binding.viewModel = messageViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        initCategorySpinner()
        return binding.root
    }

    private fun initCategorySpinner() {
        binding.averageCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                messageViewModel.actualCategoryPosition = position
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}