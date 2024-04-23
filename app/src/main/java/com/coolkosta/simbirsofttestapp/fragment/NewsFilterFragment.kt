package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.FilterAdapter
import com.coolkosta.simbirsofttestapp.viewmodel.NewsFilterViewModel

class NewsFilterFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter

    private val viewModel: NewsFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.initFilterCategories(
                arguments?.getIntegerArrayList(FILTER_CATEGORIES_KEY).orEmpty()
            )
        }
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
                            bundleOf(FILTER_EXTRA_KEY to viewModel.filterCategories)
                        )
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }
        recyclerView = view.findViewById(R.id.recycler_view_container)

        viewModel.categories.observe(viewLifecycleOwner) {
            adapter = FilterAdapter(it, viewModel.filterCategories) { position, isCheck ->
                viewModel.onSwitchChanged(position, isCheck)
            }
            recyclerView.adapter = adapter
        }
    }

    companion object {
        private const val FILTER_CATEGORIES_KEY = "selected_categories_key"
        const val REQUEST_FILTER_RESULT_KEY = "filtered_categories_result_key"
        const val FILTER_EXTRA_KEY = "extra_key"

        fun newInstance(selectedCategories: List<Int>) = NewsFilterFragment().apply {
            arguments = bundleOf(FILTER_CATEGORIES_KEY to selectedCategories)
        }
    }
}