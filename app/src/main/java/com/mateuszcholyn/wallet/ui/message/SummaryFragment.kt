package com.mateuszcholyn.wallet.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentSummaryV2Binding
import com.mateuszcholyn.wallet.fragmentViewModel
import com.mateuszcholyn.wallet.util.DateTimeChooserV2
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.view.QuickRangeSelectedListenerV2
import com.mateuszcholyn.wallet.view.QuickRangeV2
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import java.time.LocalDateTime

class SummaryFragment : Fragment(), DIAware {

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