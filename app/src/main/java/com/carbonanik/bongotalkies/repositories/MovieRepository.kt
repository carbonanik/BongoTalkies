package com.carbonanik.bongotalkies.repositories

import androidx.paging.Pager
import com.carbonanik.bongotalkies.repositories.components.MoviePagingSource
import com.carbonanik.bongotalkies.repositories.components.MoviePair
import com.carbonanik.bongotalkies.repositories.components.NetworkState
import com.carbonanik.bongotalkies.repositories.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedFeaturedMovies(): Flow<NetworkState<List<Movie>>>

    fun getTopRatedMoviesPaged(
        language: String
    ): Pager<Int, MoviePair>
}