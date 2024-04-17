package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.NewsAdapter
import com.coolkosta.simbirsofttestapp.viewmodel.NewsViewModel


class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var toolBar: Toolbar
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = view.findViewById<Toolbar?>(R.id.news_toolbar).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_filter -> {
                        openNewsFilterFragment()
                        true
                    }

                    else -> false
                }
            }
        }
        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = NewsAdapter()
        recyclerView.adapter = adapter
        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    private fun openNewsFilterFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFilterFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}