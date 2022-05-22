package com.carbonanik.bongotalkies.repositories.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.carbonanik.bongotalkies.data.remote.const_.ApiKey
import com.carbonanik.bongotalkies.data.remote.movie_api.MovieApi
import com.carbonanik.bongotalkies.data.remote.movie_api.dto.toMovies
import com.carbonanik.bongotalkies.data.remote.movie_detail_api.MovieDetailApi
import com.carbonanik.bongotalkies.repositories.MovieRepository
import com.carbonanik.bongotalkies.repositories.components.*

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDetailApi: MovieDetailApi
) : MovieRepository {

    override fun getTopRatedFeaturedMovies() = tryFetchRequest {
        movieApi.getTopRatedMovie(
            apiKey = ApiKey(),
            language = "en-US",
            page = 1
        ).results.filter {
            it.id in featuredMovieIds
        }.map {
            it.toMovies()
        }.reversed()
    }

    private val pagingConfig = PagingConfig(
        initialLoadSize = PAGE_SIZE,
        pageSize = PAGE_SIZE,
        maxSize = PAGE_SIZE * 3,
        enablePlaceholders = true
    )

    override fun getTopRatedMoviesPaged(language: String): Pager<Int, MoviePair> {
        return Pager(pagingConfig){
            MoviePagingSource(
                backend = movieApi,
                language = language
            )
        }
    }
}