package com.coolkosta.simbirsofttestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        viewPager = view.findViewById(R.id.viewpager_container)
        tabLayout = view.findViewById(R.id.tabLayout)

        viewPager.adapter = SearchResultPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.event_tab_label)
                1 -> getString(R.string.nko_tab_label)
                else -> null
            }
        }.attach()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}