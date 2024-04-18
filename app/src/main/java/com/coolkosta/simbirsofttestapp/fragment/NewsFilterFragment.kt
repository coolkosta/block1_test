package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.FilterAdapter
import com.coolkosta.simbirsofttestapp.viewmodel.NewsViewModel

class NewsFilterFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter
    private val viewModel: NewsViewModel by activityViewModels()


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
                        val filter = viewModel.filteredCategories.value ?: emptyList()
                        viewModel.filteredList(filter)
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }
        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = FilterAdapter(viewModel)
        recyclerView.adapter = adapter
        viewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = NewsFilterFragment()
    }
}