package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.HelpAdapter


class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_container)
        val adapter = HelpAdapter()
        recyclerView.layoutManager =
            GridLayoutManager(
                context,
                2,
                LinearLayoutManager.VERTICAL,
                false
            )
        recyclerView.adapter = adapter
        return view
    }

    companion object {
        fun newInstance() = HelpFragment()
    }
}
