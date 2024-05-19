package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.SearchResultPagerAdapter
import com.coolkosta.simbirsofttestapp.util.Generator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var emptyListView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewpager_container)
        tabLayout = view.findViewById(R.id.tabLayout)
        emptyListView = view.findViewById(R.id.empty_list)
        viewPager.adapter = SearchResultPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.event_tab_label)
                1 -> getString(R.string.nko_tab_label)
                else -> null
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateListForPosition(position)
            }
        })
        searchView = view.findViewById(R.id.search_view)
    }

    private fun updateListForPosition(position: Int) {
        val currentFragment = childFragmentManager.findFragmentByTag("f$position")
        if (currentFragment is SearchByEventFragment) {
            when (position) {
                0 -> {
                    val list = Generator().generateEventList()
                    currentFragment.updateList(list)
                    search(currentFragment, list)
                }

                1 -> {
                    val list = Generator().generateNkoList()
                    currentFragment.updateList(list)
                    search(currentFragment, list)
                }
            }
        }
    }

    private fun search(currentFragment: SearchByEventFragment, list: List<String>) {
        Observable.create { emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        emitter.onNext(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        emitter.onNext(newText)
                    }
                    return true
                }

            })
        }.debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { searchText ->
                    Log.d(TAG, "On Next: $searchText")
                    when (searchText) {
                        " " -> emptyListView.visibility = View.VISIBLE
                        else -> emptyListView.visibility = View.GONE
                    }
                    val newList = list.filter { it.contains(searchText, ignoreCase = true) }
                    currentFragment.updateList(newList)
                },
                {
                    Log.e(TAG, "Error: $it")
                },
                {
                    Log.d(TAG, "Complete")
                }
            ).isDisposed
    }

    companion object {
        private const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }
}