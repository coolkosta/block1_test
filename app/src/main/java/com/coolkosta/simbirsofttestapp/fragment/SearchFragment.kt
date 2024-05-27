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
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var emptyListView: View
    private lateinit var eventList: List<String>
    private lateinit var nkoList: List<String>
    private var isVisible: Boolean = true

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isVisible = false
            eventList = savedInstanceState.getStringArrayList(EVENT_STATE) as List<String>
            nkoList = savedInstanceState.getStringArrayList(NKO_STATE) as List<String>
        } else {
            eventList = Generator().generateEventList()
            nkoList = Generator().generateNkoList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(EVENT_STATE, eventList as ArrayList<String>)
        outState.putStringArrayList(NKO_STATE, nkoList as ArrayList<String>)
    }

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
        emptyListView.visibility = if (isVisible) View.VISIBLE else View.GONE

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
                    search(currentFragment, eventList)
                }

                1 -> {
                    search(currentFragment, nkoList)
                }
            }
        }
    }

    private fun search(currentFragment: SearchByEventFragment, list: List<String>) {
        searchView.queryTextChanges()
            .debounce(TIMEOUT, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                emptyListView.visibility = if (text.isNotBlank()) View.GONE else View.VISIBLE
                val newList = list.filter { it.contains(text, ignoreCase = true) }
                currentFragment.updateList(newList)
            }, { ex ->
                Log.e(SEARCH_ERROR_TAG, "onError message: ${ex.message}")
            }).addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val EVENT_STATE = "Event"
        private const val NKO_STATE = "Nko"
        private const val SEARCH_ERROR_TAG = "SearchFragmentError"
        private const val TIMEOUT = 500L
        fun newInstance() = SearchFragment()
    }
}