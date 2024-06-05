package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.SearchResultAdapter
import com.coolkosta.simbirsofttestapp.util.SearchCategory
import com.coolkosta.simbirsofttestapp.viewmodel.SearchByEventFragmentViewModel
import kotlinx.coroutines.launch

class SearchByEventFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter
    private val viewModel: SearchByEventFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val category = BundleCompat.getSerializable(
                requireArguments(),
                SEARCH_CATEGORY_KEY,
                SearchCategory::class.java
            ) as SearchCategory
            viewModel.getInitList(category)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search_by_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = SearchResultAdapter(viewModel.initList)
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResult.collect {
                    adapter.updateList(it)
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        viewModel.search(query, viewModel.initList)
    }

    companion object {
        private const val SEARCH_CATEGORY_KEY = "Search_category_key"
        fun newInstance(category: SearchCategory) = SearchByEventFragment().apply {
            arguments = bundleOf(SEARCH_CATEGORY_KEY to category)
        }
    }
}