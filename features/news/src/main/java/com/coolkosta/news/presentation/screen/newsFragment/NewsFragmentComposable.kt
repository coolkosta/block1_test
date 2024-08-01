package com.coolkosta.news.presentation.screen.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coolkosta.core.presentation.ui.theme.SimbirSoftTestAppTheme
import com.coolkosta.core.presentation.ui.theme.TurtleGreen
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailFragment
import com.coolkosta.news.presentation.screen.newsFilterFragment.NewsFilterFragment
import com.coolkosta.news.presentation.screen.newsFilterFragment.NewsFilterFragment.Companion.FILTER_EXTRA_KEY
import com.coolkosta.news.presentation.screen.newsFilterFragment.NewsFilterFragment.Companion.REQUEST_FILTER_RESULT_KEY

class NewsFragmentComposable : Fragment() {

    private val viewModel: NewsViewModel by viewModels {
        (requireActivity().application as NewsComponentProvider)
            .getNewsComponent()
            .newsViewModelFactory()
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
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SimbirSoftTestAppTheme {
                    NewsScreen(
                        viewModel,
                        onCLick = {
                            if (viewModel.state.value is NewsState.Success) {
                                openFragment(
                                    NewsFilterFragment
                                        .newInstance(
                                            (viewModel.state.value as NewsState.Success)
                                                .filterCategories
                                        )
                                )
                            }
                        },
                        onEventClick = { event ->
                            openFragment(EventDetailFragment.newInstance(event))
                            viewModel.sendEvent(NewsEvent.EventReaded(event))
                        }
                    )
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.coolkosta.core.R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = NewsFragmentComposable()
    }
}

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel,
    onCLick: () -> Unit,
    onEventClick: (EventEntity) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsScreenTopAppBar(
                newsViewModel = newsViewModel,
                onCLick = onCLick
            )
        }
    ) {
        NewsDisplay(
            modifier = Modifier.padding(it),
            newsViewModel = newsViewModel,
            onEventClick = onEventClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenTopAppBar(
    newsViewModel: NewsViewModel,
    modifier: Modifier = Modifier,
    onCLick: () -> Unit
) {
    val state by newsViewModel.state.collectAsStateWithLifecycle()
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TurtleGreen
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(id = com.coolkosta.core.R.string.news)
                )
            }
        },
        modifier = modifier,
        actions = {
            if (state is NewsState.Success) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = com.coolkosta.core.R.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onCLick()
                        }
                        .padding(end = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_l))
                )
            }
        }
    )
}