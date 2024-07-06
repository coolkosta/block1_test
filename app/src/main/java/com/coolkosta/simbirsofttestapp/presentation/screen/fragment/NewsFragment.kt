package com.coolkosta.simbirsofttestapp.presentation.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.NewsState
import com.coolkosta.simbirsofttestapp.presentation.adapter.NewsAdapter
import com.coolkosta.simbirsofttestapp.presentation.screen.fragment.NewsFilterFragment.Companion.FILTER_EXTRA_KEY
import com.coolkosta.simbirsofttestapp.presentation.screen.fragment.NewsFilterFragment.Companion.REQUEST_FILTER_RESULT_KEY
import com.coolkosta.simbirsofttestapp.presentation.viewmodel.NewsViewModel
import com.coolkosta.simbirsofttestapp.util.getAppComponent
import com.google.android.material.progressindicator.CircularProgressIndicator


class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var toolBar: Toolbar
    private lateinit var progress: CircularProgressIndicator
    private val viewModel: NewsViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_FILTER_RESULT_KEY) { _, bundle ->
            val filteredList = bundle.getIntegerArrayList(FILTER_EXTRA_KEY) as List<Int>
            viewModel.onCategoriesChanged(filteredList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = view.findViewById<Toolbar>(R.id.news_toolbar).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_filter -> {
                        openFragment(NewsFilterFragment.newInstance(viewModel.filterCategories))
                        true
                    }

                    else -> false
                }
            }
        }

        progress = view.findViewById(R.id.progress_circular)
        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = NewsAdapter { event ->
            viewModel.readEvent(event)
            openFragment(EventDetailFragment.newInstance(event))
        }

        recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NewsState.Loading -> {
                    setupVisibility(true)
                }

                is NewsState.Success -> {
                    setupVisibility(false)
                    adapter.submitList(state.events)
                }

                is NewsState.Error -> {
                    setupVisibility(false)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_loading_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupVisibility(isVisible: Boolean) {
        progress.isVisible = isVisible
        toolBar.menu.findItem(R.id.action_filter).isVisible = !isVisible
        recyclerView.isVisible = !isVisible
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}