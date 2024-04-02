package com.coolkosta.simbirsofttestapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coolkosta.simbirsofttestapp.fragment.SearchByEventFragment

class SearchResultPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        SearchByEventFragment.newInstance(),
        SearchByEventFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}