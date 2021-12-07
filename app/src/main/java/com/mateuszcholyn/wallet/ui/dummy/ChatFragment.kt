package com.mateuszcholyn.wallet.ui.dummy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.fragmentViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class ChatFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val chatViewModel: ChatViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                BigText()
            }
        }

    @Preview
    @Composable
    fun BigText() {
        Text(chatViewModel.textChat, fontSize = 30.sp)
    }

}