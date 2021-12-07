package com.mateuszcholyn.wallet.ui.dummy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.mateuszcholyn.wallet.fragmentViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class ChatFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val chatViewModel: ChatViewModel by fragmentViewModel()

    val countriesList = mutableListOf("India", "USA", "Canada", "Germany", "Australia")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View =
            ComposeView(requireContext()).apply {
                setContent {
                    ChatView()
                }
            }

    @Preview
    @Composable
    fun ChatView() {
        // State variables
        var countryName: String by remember { mutableStateOf(countriesList[0]) }
        var expanded by remember { mutableStateOf(false) }

        MyTheme {


            Box(Modifier.width(IntrinsicSize.Max)) {
                Row(
                        modifier = Modifier.clickable { expanded = !expanded },
                        verticalAlignment = Alignment.CenterVertically,
                ) { // Anchor view
                    Text(text = countryName, fontSize = 18.sp,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(end = 8.dp))
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")

                    //
                    DropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                    }) {
                        countriesList.forEach { country ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                countryName = country
                            }) {
                                Text(text = country)
                            }
                        }
                    }
                }
            }
        }
    }

}