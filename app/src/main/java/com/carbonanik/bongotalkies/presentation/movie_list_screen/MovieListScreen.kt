@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.carbonanik.bongotalkies.presentation.movie_list_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.carbonanik.bongotalkies.data.remote.const_.HttpRoutes.IMAGE_URL
import com.carbonanik.bongotalkies.presentation.component.LoadingAnimation
import com.carbonanik.bongotalkies.presentation.destinations.MovieDetailScreenDestination
import com.carbonanik.bongotalkies.repositories.components.MoviePair
import com.carbonanik.bongotalkies.repositories.model.Movie
import com.carbonanik.bongotalkies.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.abs


@Destination(start = true)
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val movies = viewModel.moviePage.collectAsLazyPagingItems()
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            val lazyListState = rememberLazyListState()
            var scrolledY = 0f
            var previousOffset = 0
            var totalScroll = 0f
            var alpha by remember { mutableStateOf(1f) }

            if (movies.itemCount == 0) {
                LoadingAnimation(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 150.dp)
                        .align(Alignment.Center)
                )
            } else {
                TopRatedMovieGrid(
                    modifier = Modifier,
                    movies = movies,
                    scrollState = lazyListState
                ) {
                    navigator.navigate(MovieDetailScreenDestination(it))
                }
            }

            Box(
                Modifier
                    .graphicsLayer {
                        scrolledY -= lazyListState.firstVisibleItemScrollOffset - previousOffset
                        totalScroll = (scrolledY * 0.5f) +
                                lazyListState.firstVisibleItemIndex * -490
                        translationY = totalScroll
                        alpha = 1f - (abs(totalScroll) / 600).coerceIn(0f, 1f)
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
            ) {
                HorizontalPagerWithOffsetTransition(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .graphicsLayer {
                            this.alpha = alpha
                        },
                    movies = viewModel.featuredMovieList,
                    onClick = {
                        navigator.navigate(MovieDetailScreenDestination(it))
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopRatedMovieGrid(
    movies: LazyPagingItems<MoviePair>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(top = 400.dp),
        content = {

//            stickyHeader {
//                Text(modifier = Modifier., text = "Top Rated", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            }

            items(movies) { movie ->
                if (movie != null) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MovieItem(movie = movie.m1, onClick = {
                            onClick(movie.m1)
                        }, modifier = Modifier.weight(1f))
                        MovieItem(movie = movie.m2, onClick = {
                            onClick(movie.m2)
                        }, modifier = Modifier.weight(1f))
                    }
                }
            }

            if (movies.loadState.append == LoadState.Loading) {
                item {
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }

        })
}


@Composable
private fun MovieItem(movie: Movie, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val url = remember(movie.posterPath) { IMAGE_URL(movie.posterPath) }

    Column(
        modifier = modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
                .padding(16.dp)
        ) {

            Image(
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(
                    data = url,
                    builder = { crossfade(true) }
                ),
                contentDescription = null
            )
        }

        Text(
            text = movie.title,
            textAlign = TextAlign.Center,
            color = Purple500,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}