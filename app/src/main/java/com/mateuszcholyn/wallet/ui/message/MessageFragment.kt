package com.mateuszcholyn.wallet.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentMessageBinding
import com.mateuszcholyn.wallet.fragmentViewModel
import com.mateuszcholyn.wallet.util.DateTimeChooserV2
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import java.time.LocalDateTime

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
        initDateTimePickers()
        return binding.root
    }

    private fun initCategorySpinner() {
        binding.averageCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    messageViewModel.actualCategoryPosition = position
                }
            }
    }

    private fun initDateTimePickers() {
        DateTimeChooserV2(
            LocalDateTime.now().atStartOfTheDay(),
            requireContext(),
            messageViewModel.beginDate,
            binding.summaryBeginDateTimePicker
        )

        DateTimeChooserV2(
            LocalDateTime.now(),
            requireContext(),
            messageViewModel.endDate,
            binding.summaryEndDateTimePicker
        )

//        QuickRange.setDefaultDates(mBeginDate, mEndDate)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}