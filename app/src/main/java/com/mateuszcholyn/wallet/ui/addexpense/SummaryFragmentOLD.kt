package com.mateuszcholyn.wallet.ui.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.activityScopedFragmentViewModel
import com.mateuszcholyn.wallet.databinding.FragmentSummaryBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class SummaryFragmentOLD : Fragment(), DIAware {
    override val di: DI by closestDI()
    private val summaryViewModelOLD: SummaryViewModelOLD by activityScopedFragmentViewModel()

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
        observeViewModel()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.summaryButton.setOnClickListener { summaryViewModelOLD.setActualValue() }
    }

    private fun observeViewModel() {
        summaryViewModelOLD.textSummaryLiveData.observe(viewLifecycleOwner, { newText ->
            val textView: TextView = binding.summaryText
            textView.text = newText
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }
}