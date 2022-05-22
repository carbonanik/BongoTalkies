package com.carbonanik.bongotalkies.presentation.movie_list_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbonanik.bongotalkies.repositories.MovieRepository
import com.carbonanik.bongotalkies.repositories.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val featuredMovieList = mutableStateListOf<Movie>()
    var isFeaturedLoading by mutableStateOf(false)
        private set

    val moviePage = movieRepository.getTopRatedMoviesPaged("en-US").flow

    init {
        viewModelScope.launch {
            movieRepository.getTopRatedFeaturedMovies().collect {
                featuredMovieList.addAll(it.data ?: emptyList())
            }
        }
    }

}