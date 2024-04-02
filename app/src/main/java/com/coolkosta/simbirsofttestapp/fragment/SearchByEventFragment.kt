package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.SearchResultAdapter

class SearchByEventFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_by_event, container, false)
        recyclerView= view.findViewById(R.id.recycler_view_container)
        adapter = SearchResultAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        recyclerView.adapter = adapter
        return view
    }

    fun updateList(newList: List<String>) {
        adapter.updateList(newList)
    }


    companion object {
      fun newInstance() = SearchByEventFragment()
    }
}