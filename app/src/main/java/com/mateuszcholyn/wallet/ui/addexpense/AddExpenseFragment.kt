package com.mateuszcholyn.wallet.ui.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mateuszcholyn.wallet.databinding.FragmentAddExpenseBinding

class AddExpenseFragment : Fragment() {

    private lateinit var addExpenseViewModel: AddExpenseViewModel
    private var _binding: FragmentAddExpenseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addExpenseViewModel =
            ViewModelProvider(this).get(AddExpenseViewModel::class.java)

        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        addExpenseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}