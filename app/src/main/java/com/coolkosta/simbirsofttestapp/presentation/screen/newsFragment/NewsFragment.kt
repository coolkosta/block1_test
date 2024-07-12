package com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.adapter.NewsAdapter
import com.coolkosta.simbirsofttestapp.presentation.screen.eventDetailFragment.EventDetailFragment
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment.NewsFilterFragment
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment.NewsFilterFragment.Companion.FILTER_EXTRA_KEY
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment.NewsFilterFragment.Companion.REQUEST_FILTER_RESULT_KEY
import com.coolkosta.simbirsofttestapp.util.getAppComponent
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolBar: Toolbar
    private lateinit var progress: CircularProgressIndicator

    private val viewModel: NewsViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }
    private val adapter: NewsAdapter by lazy {
        NewsAdapter { event ->
            viewModel.sendEvent(NewsEvent.EventReaded(event))
            openFragment(EventDetailFragment.newInstance(event))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_FILTER_RESULT_KEY) { _, bundle ->
            val filteredList = bundle.getIntegerArrayList(FILTER_EXTRA_KEY) as List<Int>
            viewModel.sendEvent(NewsEvent.EventsFiltered(filteredList))
        }
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
                        if (viewModel.state.value is NewsState.Success) {
                            openFragment(
                                NewsFilterFragment
                                    .newInstance(
                                        (viewModel.state.value as NewsState.Success).filterCategories
                                    )
                            )
                        } else if (viewModel.state.value is NewsState.Error) {
                            openFragment(
                                NewsFilterFragment.newInstance(emptyList())
                            )
                        }
                        true
                    }

                    else -> false
                }
            }
        }

        progress = view.findViewById(R.id.progress_circular)
        recyclerView = view.findViewById(R.id.recycler_view_container)
        recyclerView.adapter = adapter
        observeNewsFragmentViewModel()
    }

    private fun observeNewsFragmentViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is NewsState.Loading -> {
                                setupVisibility(true)
                            }

                            is NewsState.Success -> {
                                setupVisibility(false)
                                adapter.submitList(state.eventEntities)
                            }

                            is NewsState.Error -> {
                                setupVisibility(false)
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { state ->
                        if (state is NewsSideEffect.ShowErrorToast) {
                            showToast(state.message)
                        }
                    }
                }
            }
        }
    }

    private fun setupVisibility(isVisible: Boolean) {
        progress.isVisible = isVisible
        toolBar.menu.findItem(R.id.action_filter).isVisible = !isVisible
        recyclerView.isVisible = !isVisible
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_loading_toast) + " " + message,
            Toast.LENGTH_SHORT
        ).show()
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