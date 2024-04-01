package com.coolkosta.simbirsofttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchByNkoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_by_nko, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_container)
        val adapter = SearchByNkoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        recyclerView.adapter = adapter
        return view
    }

    companion object {
      @JvmStatic
      fun newInstance() = SearchByNkoFragment()
    }
}