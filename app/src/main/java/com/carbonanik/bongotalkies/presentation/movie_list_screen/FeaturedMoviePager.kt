@file:OptIn(ExperimentalPagerApi::class)

package com.carbonanik.bongotalkies.presentation.movie_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.carbonanik.bongotalkies.data.remote.const_.HttpRoutes
import com.carbonanik.bongotalkies.repositories.model.Movie
import com.carbonanik.bongotalkies.ui.theme.Background
import com.carbonanik.bongotalkies.ui.theme.Purple500
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
internal fun HorizontalPagerWithOffsetTransition(
    movies: SnapshotStateList<Movie>,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = movies.size,
        contentPadding = PaddingValues(horizontal = 42.dp),
        modifier = modifier.fillMaxWidth()
    ) { page ->

        val movie = remember(page) { movies[page] }

        FeaturedMovieCard(
            page = page,
            pagerScope = this,
            onClick = {
                onClick(movie)
            },
            title = {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                    text = movie.title.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Purple500
                )
            },
            posterImage = {
                Image(
                    painter = rememberImagePainter(data = HttpRoutes.IMAGE_URL(movie.posterPath)),
                    contentDescription = null,
                    modifier = Modifier
                        .height(235.dp)
                        .aspectRatio(.65f),
                    contentScale = ContentScale.Crop
                )
            },
            voteAverage = {
                Text(
                    text = movie.voteAverage.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Background
                )
            },
            backdropImage = {
                Image(
                    painter = rememberImagePainter(
                        data = HttpRoutes.IMAGE_URL(movie.backdropPath),
                        builder = {
                            this.transformations(
                                BlurTransformation(LocalContext.current, 5f, 5f)
                            )
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            })
    }
}

@Composable
private fun PagerScope.FeaturedMovieCard(
    page: Int,
    pagerScope: PagerScope,
    title: @Composable RowScope.() -> Unit,
    posterImage: @Composable () -> Unit,
    voteAverage: @Composable BoxScope.() -> Unit,
    backdropImage: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                lerp(
                    start = 0.85f.toDp(),
                    stop = 1f.toDp(),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.toPx()
                    scaleY = scale.toPx()
                }

                alpha = lerp(
                    start = 0.5f.toDp(),
                    stop = 1f.toDp(),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).toPx()
            }
            .fillMaxWidth()
            .aspectRatio(.85f),
        shape = RoundedCornerShape(46.dp)
    ) {
        Box {

            backdropImage()

            // Movie Info Parallax Box
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .offset {
                        val pageOffset =
                            pagerScope.calculateCurrentOffsetForPage(page)
                        IntOffset(
                            x = (360.dp * pageOffset).roundToPx(),
                            y = 0
                        )
                    }
                    .width(280.dp)
                    .background(Color.Black.copy(.3f), shape = RoundedCornerShape(36.dp))
            ) {

                // Poster And Side Info
                Row {

                    // Poster
                    Card(
                        modifier = Modifier,
                        shape = RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp),
                        elevation = 2.dp
                    ) {
                        posterImage()
                    }
                    title()
                }

                // Rating Box
                Box(
                    modifier = Modifier
                        .padding(bottom = 24.dp, start = 24.dp)
                        .size(55.dp)
                        .background(Purple500, RoundedCornerShape(16.dp))
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    voteAverage()
                }
            }

            // Play Movie button
            Box(
                modifier = Modifier
                    .padding(bottom = 26.dp)
                    .offset {
                        val pageOffset =
                            pagerScope.calculateCurrentOffsetForPage(page)
                        IntOffset(
                            x = (360.dp * (-pageOffset)).roundToPx(),
                            y = 0
                        )
                    }
                    .height(45.dp)
                    .background(Color.Black.copy(.5f), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp)
                    .align(Alignment.BottomCenter)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onClick() },
                contentAlignment = Alignment.Center

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    // Play Icon
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp)
                            .background(color = Purple500.copy(.5f), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Triangle
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.Black.copy(.5f)
                        )
                    }

                    Text(
                        text = "Play Movie",
                        fontWeight = FontWeight.Bold,
                        color = Purple500,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}