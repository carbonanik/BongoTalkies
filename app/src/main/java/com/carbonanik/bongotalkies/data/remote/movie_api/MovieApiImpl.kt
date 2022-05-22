package com.carbonanik.bongotalkies.data.remote.movie_api

import com.carbonanik.bongotalkies.data.remote.const_.HttpRoutes
import com.carbonanik.bongotalkies.data.remote.const_.ParameterKey
import com.carbonanik.bongotalkies.data.remote.movie_api.dto.MovieListResponseDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieApiImpl(private val client: HttpClient) : MovieApi {

    override suspend fun getTopRatedMovie(
        apiKey: String,
        language: String,
        page: Int
    ): MovieListResponseDto = withContext(Dispatchers.IO) {
        client.get {
            parameter(ParameterKey.API_KEY, apiKey)
            parameter(ParameterKey.LANGUAGE, language)
            parameter(ParameterKey.PAGE, page)
            url(HttpRoutes.TOP_RATED)
            println("getTopRatedMovie")
        }
    }
}
