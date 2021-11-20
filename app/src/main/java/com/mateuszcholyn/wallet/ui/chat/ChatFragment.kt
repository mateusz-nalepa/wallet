package com.mateuszcholyn.wallet.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var bindingNullable: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentChatBinding.inflate(inflater, container, false)
//        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
//        initOnClickListeners()
    }

//    private fun initOnClickListeners() {
//        binding.summaryButton.setOnClickListener { summaryViewModel.setActualValue() }
//    }

    private fun observeViewModel() {
        binding.chatId.text = (1..100).random().toString()
    //        summaryViewModel.textSummaryLiveData.observe(viewLifecycleOwner, { newText ->
//            val textView: TextView = binding.chatId
//            textView.text = newText
//        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}