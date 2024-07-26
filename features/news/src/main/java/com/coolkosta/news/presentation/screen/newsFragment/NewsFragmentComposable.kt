package com.coolkosta.news.presentation.screen.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.coolkosta.core.presentation.ui.theme.SimbirSoftTestAppTheme
import com.coolkosta.core.presentation.ui.theme.TurtleGreen
import com.coolkosta.news.R
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
                                            (viewModel.state.value as NewsState.Success).filterCategories
                                        )
                                )
                            } else if (viewModel.state.value is NewsState.Error) {
                                openFragment(
                                    NewsFilterFragment.newInstance(emptyList())
                                )
                            }
                        },
                        onEventClick = { event ->
                            openFragment(EventDetailFragment.newInstance(event))
                            viewModel.sendEvent(NewsEvent.EventReaded(event))
                        },
                        showErrorMessage = { message ->
                            showToast(message)
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

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_loading_toast) + " " + message,
            Toast.LENGTH_SHORT
        ).show()
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
    showErrorMessage: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsScreenTopAppBar(onCLick = onCLick)
        }
    ) {
        NewsDisplay(
            modifier = Modifier.padding(it),
            newsViewModel = newsViewModel,
            onEventClick = onEventClick,
            showErrorMessage = showErrorMessage
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenTopAppBar(modifier: Modifier = Modifier, onCLick: () -> Unit) {
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
            Icon(
                tint = Color.White,
                painter = painterResource(id = com.coolkosta.core.R.drawable.ic_filter),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onCLick()
                }
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsScreenPreview() {
    SimbirSoftTestAppTheme {
        NewsScreenTopAppBar(onCLick = {})
    }
}

