package com.carbonanik.bongotalkies.presentation.movie_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.carbonanik.bongotalkies.data.remote.const_.HttpRoutes
import com.carbonanik.bongotalkies.data.remote.movie_api.dto.MovieItemDto
import com.carbonanik.bongotalkies.data.remote.movie_api.dto.toMovies
import com.carbonanik.bongotalkies.presentation.component.formatDate
import com.carbonanik.bongotalkies.repositories.model.Movie
import com.ramcosta.composedestinations.annotation.Destination


@Destination
@Composable
fun MovieDetailScreen(
    movie: Movie
) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            Backdrop(movie.backdropPath)
            ActionBar()
            Column(Modifier.padding(horizontal = 24.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp, start = 12.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    MoviePoster(movie.posterPath)
                    PlayButton(Modifier.padding(bottom = 10.dp))
                }
                TitleAndReleaseDate(movie)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Rating(movie.voteAverage.toString(), movie.voteCount)
                    AgeRestriction(ageRestricted = movie.adult)
                }
                Overview(movie.overview, Modifier.padding(top = 16.dp))
                Recommended()
            }
        }

    }
}

@Composable
fun ActionBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White.copy(.0f)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = null
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

@Composable
fun Backdrop(
    backdropPath: String,
    modifier: Modifier = Modifier
) {
    Box {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = HttpRoutes.IMAGE_URL(backdropPath),
                builder = {
                    crossfade(true)
                    transformations(BlurTransformation(LocalContext.current, 5f, 5f))
                }), contentDescription = null
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(.2f)))
    }
}

@Composable
fun MoviePoster(posterPath: String, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .height(260.dp)
            .width(180.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop,
        painter = rememberImagePainter(
            data = HttpRoutes.IMAGE_URL(posterPath),
            builder = {
                crossfade(true)
            }),
        contentDescription = null
    )
}

@Composable
fun PlayButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(80.dp)
            .background(Color.Red, CircleShape)
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center),
            tint = Color.White,
            imageVector = Icons.Rounded.PlayArrow, contentDescription = null
        )
    }
}

@Composable
fun TitleAndReleaseDate(movie: Movie) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = movie.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movie.releaseDate.formatDate(),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun Rating(
    rating: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Gray)
        Text(text = rating, color = Color.Gray)
//        Spacer(modifier = Modifier.width(0.dp))
        Text(text = "$count votes", color = Color.DarkGray)
    }
}

@Composable
fun AgeRestriction(ageRestricted: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.DarkGray.copy(.5f), RoundedCornerShape(4.dp))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = if (ageRestricted) "18+" else "13+",
            color = Color.Gray
        )
    }
}

@Composable
fun Overview(overview: String, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = "Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        var expanded by remember { mutableStateOf(false) }
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .animateContentSize(),
            text = overview,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            color = Color.Gray
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }, text = "Read More", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Composable
fun Recommended() {

}


@Preview
@Composable
fun DetailPrev() {
    MovieDetailScreen(MovieItemDto().toMovies())
}

