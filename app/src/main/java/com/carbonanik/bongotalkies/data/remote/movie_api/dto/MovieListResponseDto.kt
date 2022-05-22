package com.carbonanik.bongotalkies.data.remote.movie_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponseDto(
    @SerialName("page")
    var page: Int? = null,
    @SerialName("results")
    var results: List<MovieItemDto> = emptyList(),
    @SerialName("total_pages")
    var totalPages: Int? = null,
    @SerialName("total_results")
    var totalResults: Int? = null
)
