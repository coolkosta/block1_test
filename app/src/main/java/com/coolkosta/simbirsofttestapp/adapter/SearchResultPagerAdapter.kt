package com.coolkosta.simbirsofttestapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coolkosta.simbirsofttestapp.fragment.SearchByEventFragment
import com.coolkosta.simbirsofttestapp.util.SearchCategory

class SearchResultPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        SearchByEventFragment.newInstance(SearchCategory.EVENTS),
        SearchByEventFragment.newInstance(SearchCategory.NKO)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}