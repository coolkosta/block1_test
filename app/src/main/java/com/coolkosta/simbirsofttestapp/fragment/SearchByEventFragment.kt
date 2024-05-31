package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.SearchResultAdapter
import com.coolkosta.simbirsofttestapp.util.SearchCategory
import com.coolkosta.simbirsofttestapp.viewmodel.SearchFragmentByEventViewModel

class SearchByEventFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter
    private val viewModel: SearchFragmentByEventViewModel by viewModels()
    private var list: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val category = requireArguments().getString(SEARCH_CATEGORY_KEY).orEmpty()
            when (category) {
                SearchCategory.EVENTS.nameCategory -> {
                    viewModel.generateEventList()
                    list = viewModel.eventList ?: emptyList()
                }

                SearchCategory.NKO.nameCategory -> {
                    viewModel.generateNkoList()
                    list = viewModel.nkoList ?: emptyList()
                }
            }
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
        adapter = SearchResultAdapter(list)
        recyclerView.adapter = adapter
        viewModel.searchResult.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    fun updateSearchQuery(query: String) {
        viewModel.search(query, list)
    }

    companion object {
        private const val SEARCH_CATEGORY_KEY = "Search_category_key"
        fun newInstance(nameCategory: String) = SearchByEventFragment().apply {
            arguments = bundleOf(SEARCH_CATEGORY_KEY to nameCategory)
        }
    }
}