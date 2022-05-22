package com.carbonanik.bongotalkies.data.remote.movie_api

import com.carbonanik.bongotalkies.data.remote.movie_api.dto.MovieListResponseDto

interface MovieApi {
    suspend fun getTopRatedMovie(
        apiKey: String,
        language: String,
        page: Int
    ): MovieListResponseDto
}