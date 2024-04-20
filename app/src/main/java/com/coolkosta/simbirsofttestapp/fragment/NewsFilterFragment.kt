package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.FilterAdapter
import com.coolkosta.simbirsofttestapp.util.EventCategory
import com.coolkosta.simbirsofttestapp.viewmodel.NewsFilterViewModel
import java.util.ArrayList

class NewsFilterFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter
    private lateinit var categories: List<EventCategory>
    private var filteredList: List<Int>? = null

    private val viewModel: NewsFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filteredList = savedInstanceState?.getIntegerArrayList("test") as List<Int>?
            ?: viewModel.filteredList.value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById<Toolbar>(R.id.event_detail_toolbar).apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_done -> {
                        viewModel.filteredList.observe(viewLifecycleOwner) {
                            setFragmentResult(
                                "request_key",
                                bundleOf("extra_key" to it)
                            )
                        }
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }
        categories = viewModel.categories.value ?: emptyList()
        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = FilterAdapter(
            categories,
            filteredList
        ) { position, isCheck ->
            viewModel.onSwitchChanged(position, isCheck)
        }
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val savedFilterList = viewModel.filteredList.value?.toList()
        outState.putIntegerArrayList("test", savedFilterList as ArrayList<Int>)
    }

    companion object {
        fun newInstance() = NewsFilterFragment()
    }
}