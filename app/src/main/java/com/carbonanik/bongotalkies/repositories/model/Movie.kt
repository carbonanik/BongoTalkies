package com.carbonanik.bongotalkies.repositories.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var adult: Boolean,
    var releaseDate: String,
    var originalLanguage: String,

    var posterPath: String,
    var backdropPath: String,

    var overview: String,
    var voteAverage: Float,
    var voteCount: Int
): Parcelable