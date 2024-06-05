package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.SearchResultPagerAdapter
import com.coolkosta.simbirsofttestapp.viewmodel.SearchFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var emptyListView: View
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.search_view)
        viewPager = view.findViewById(R.id.viewpager_container)
        tabLayout = view.findViewById(R.id.tabLayout)
        emptyListView = view.findViewById(R.id.empty_list)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = SearchResultPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.event_tab_label)
                1 -> getString(R.string.nko_tab_label)
                else -> null
            }
        }.attach()
        queryTextListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchText.collect {
                    emptyListView.visibility =
                        if (it.isNotBlank()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun setQueryText(query: String) {
        viewPager.adapter?.itemCount?.let { itemCount ->
            for (position in 0..itemCount) {
                childFragmentManager.findFragmentByTag("f$position").let { fragment ->
                    if (fragment is SearchByEventFragment) {
                        fragment.updateSearchQuery(query)
                    }
                }
            }
        }
    }

    private fun queryTextListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text ->
                    viewModel.onSearchViewTextChanged(text)
                    setQueryText(text)
                }
                return false
            }
        })
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}