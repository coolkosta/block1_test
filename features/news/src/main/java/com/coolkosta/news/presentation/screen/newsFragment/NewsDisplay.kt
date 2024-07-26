package com.coolkosta.news.presentation.screen.newsFragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.coolkosta.core.presentation.ui.theme.BlueGery
import com.coolkosta.core.presentation.ui.theme.SimbirSoftTestAppTheme
import com.coolkosta.core.presentation.ui.theme.TurtleGreen
import com.coolkosta.news.R
import com.coolkosta.news.domain.model.EventEntity
import kotlinx.coroutines.launch

@Composable
fun NewsDisplay(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel,
    onEventClick: (EventEntity) -> Unit,
    showErrorMessage: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val state by newsViewModel.state.collectAsStateWithLifecycle()

    coroutineScope.launch {
        newsViewModel.sideEffect.collect { state ->
            if (state is NewsSideEffect.ShowErrorToast) {
                showErrorMessage(state.message)
            }
        }
    }

    when (state) {
        is NewsState.Error -> {
        }

        is NewsState.Loading -> {

        }

        is NewsState.Success -> {
            LazyColumn(
                modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = (state as NewsState.Success).eventEntities,
                    key = { event -> event.id }
                ) { event ->
                    NewsItem(
                        event = event,
                        onEventClick = { onEventClick(event) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
    event: EventEntity,
    modifier: Modifier = Modifier,
    onEventClick: (EventEntity) -> Unit
) {
    Card(
        onClick = { onEventClick(event) },
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
            ) {
                GlideImage(
                    model = event.imageName,
                    contentDescription = null,
                    loading = placeholder(R.drawable.ic_placeholder),
                    failure = placeholder(R.drawable.ic_placeholder),
                    modifier = Modifier
                        .matchParentSize()
                        .padding(bottom = 24.dp),
                    contentScale = ContentScale.FillWidth

                )
                Image(
                    painter = painterResource(id = R.drawable.fade),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(bottom = 24.dp)
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueGery,
                    text = event.title,
                )
            }
            Image(
                painter = painterResource(id = com.coolkosta.core.R.drawable.decor),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(24.dp)
            )
            Text(
                text = event.description,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis

            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_l))
                    .background(TurtleGreen), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    SimbirSoftTestAppTheme {
        NewsItem(
            event = EventEntity(
                1,
                listOf(3, 4),
                "",
                stringResource(id = R.string.sample_event_title),
                stringResource(id = R.string.sample_event_description_text),
                "24.08.2024",
                "",
                "",
                ""
            ),
            onEventClick = {}
        )
    }
}
