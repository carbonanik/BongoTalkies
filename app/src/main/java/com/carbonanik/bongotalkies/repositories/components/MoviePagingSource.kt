package com.carbonanik.bongotalkies.repositories.components

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.carbonanik.bongotalkies.data.remote.const_.ApiKey
import com.carbonanik.bongotalkies.data.remote.movie_api.MovieApi
import com.carbonanik.bongotalkies.data.remote.movie_api.dto.toMovies
import kotlinx.coroutines.delay
import java.io.IOException

const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 20 / 2

class MoviePagingSource(
    private val backend: MovieApi,
    private val language: String

) : PagingSource<Int, MoviePair>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePair> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX

            delay(1000)

            val response = backend.getTopRatedMovie(
                language = language,
                page = pageNumber,
                apiKey = ApiKey()
            )

            // Compose grid view has some issue
            // This is a hack for making grid
            val movies = response.results.map { it.toMovies() }
            val pairMovies: MutableList<MoviePair> = mutableListOf()

            repeat(movies.size/2) {
                pairMovies.add(
                    MoviePair(
                        movies[it * 2],
                        movies[it * 2 + 1]
                    )
                )
            }

            LoadResult.Page(
                data = pairMovies,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null
                else pageNumber - (movies.size / PAGE_SIZE),
                nextKey = if (movies.isEmpty()) null
                else pageNumber + (movies.size / PAGE_SIZE)
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviePair>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

