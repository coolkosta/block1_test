package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.NewsAdapter
import com.coolkosta.simbirsofttestapp.fragment.NewsFilterFragment.Companion.FILTER_EXTRA_KEY
import com.coolkosta.simbirsofttestapp.fragment.NewsFilterFragment.Companion.REQUEST_FILTER_RESULT_KEY
import com.coolkosta.simbirsofttestapp.viewmodel.NewsViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator


class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var toolBar: Toolbar
    private var isMenuVisible = false
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_FILTER_RESULT_KEY) { _, bundle ->
            val filteredList = bundle.getIntegerArrayList(FILTER_EXTRA_KEY) as List<Int>
            viewModel.onCategoriesChanged(filteredList)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_filter).isVisible = isMenuVisible
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = view.findViewById<Toolbar>(R.id.news_toolbar).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_filter -> {
                        openFragment(NewsFilterFragment.newInstance(viewModel.filterCategories))
                        true
                    }

                    else -> false
                }
            }
        }

        val progress: CircularProgressIndicator = view.findViewById(R.id.progress_circular)

        viewModel.progress.observe(viewLifecycleOwner) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
            isMenuVisible = !it
            invalidateOptionsMenu(activity)
        }

        recyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = NewsAdapter {
            openFragment(EventDetailFragment.newInstance(it))
        }
        recyclerView.adapter = adapter
        viewModel.eventList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}