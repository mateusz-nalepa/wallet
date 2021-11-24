package com.mateuszcholyn.wallet.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mateuszcholyn.wallet.databinding.FragmentCategoryBinding
import com.mateuszcholyn.wallet.fragmentViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class CategoryFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val categoryViewModel: CategoryViewModel by fragmentViewModel()

    private var bindingNullable: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNullable = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = categoryViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel!!.categoryList.observe(viewLifecycleOwner, { categories ->
            categories_recycler_view_v2.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = CategoryAdapterV2(binding.viewModel!!.categoryService, categories)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

}