package com.carbonanik.bongotalkies.data.remote.movie_api.dto

import com.carbonanik.bongotalkies.repositories.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItemDto(
    @SerialName("adult")
    var adult: Boolean? = null,
    @SerialName("backdrop_path")
    var backdropPath: String? = null,
    @SerialName("genre_ids")
    var genreIds: List<Int> = emptyList(),
    @SerialName("id")
    var id: Int? = null,
    @SerialName("original_language")
    var originalLanguage: String? = null,
    @SerialName("original_title")
    var originalTitle: String? = null,
    @SerialName("overview")
    var overview: String? = null,
    @SerialName("popularity")
    var popularity: Double? = null,
    @SerialName("poster_path")
    var posterPath: String? = null,
    @SerialName("release_date")
    var releaseDate: String? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("video")
    var video: Boolean? = null,
    @SerialName("vote_average")
    var voteAverage: Double? = null,
    @SerialName("vote_count")
    var voteCount: Int? = null
)

fun MovieItemDto.toMovies(): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        adult = adult ?: false,
        releaseDate = releaseDate ?: "",
        originalLanguage = originalLanguage ?: "",
        posterPath = posterPath ?: "",
        backdropPath = backdropPath ?: "",
        overview = overview ?: "",
        voteCount = voteCount ?: 0,
        voteAverage = voteAverage?.toFloat() ?: 0f
    )
}
