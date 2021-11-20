package com.mateuszcholyn.wallet.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.databinding.FragmentChatBinding
import com.mateuszcholyn.wallet.fragmentViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class ChatFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val chatViewModel: ChatViewModel by fragmentViewModel()

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
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.chatButton.setOnClickListener { chatViewModel.setActualValue() }
    }

    private fun observeViewModel() {
        chatViewModel.setActualValue()
        chatViewModel.textChatLiveData.observe(viewLifecycleOwner, { newText ->
            binding.chatId.text = newText
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}