package com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.adapter.FilterAdapter
import com.coolkosta.simbirsofttestapp.util.getAppComponent
import kotlinx.coroutines.launch

class NewsFilterFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter

    private val viewModel: NewsFilterViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoriesList = requireArguments().getIntegerArrayList(FILTER_CATEGORIES_KEY).orEmpty()
        viewModel.sendEvent(NewsFilterEvent.FilterCategorySelected(categoriesList))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById<Toolbar>(R.id.event_detail_toolbar).apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_done -> {
                        setFragmentResult(
                            REQUEST_FILTER_RESULT_KEY,
                            bundleOf(FILTER_EXTRA_KEY to viewModel.state.value.filteredList)
                        )
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }
        recyclerView = view.findViewById(R.id.recycler_view_container)

        adapter = FilterAdapter { position, isCheck ->
            viewModel.sendEvent(NewsFilterEvent.SwitchChanged(position, isCheck))
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.categories, state.filteredList)
                }
            }
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NewsFilterSideEffect.ShowErrorToast -> {
                        Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    companion object {
        const val REQUEST_FILTER_RESULT_KEY = "filtered_categories_result_key"
        const val FILTER_EXTRA_KEY = "extra_key"
        private const val FILTER_CATEGORIES_KEY = "selected_categories_key"

        fun newInstance(selectedCategories: List<Int>) = NewsFilterFragment().apply {
            arguments = bundleOf(FILTER_CATEGORIES_KEY to selectedCategories)
        }
    }
}