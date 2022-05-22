package com.carbonanik.bongotalkies.data.remote.const_

object HttpRoutes {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val a = "https://api.themoviedb.org/3/movie/top_rated?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US&page=1"

    const val TOP_RATED = "${BASE_URL}movie/top_rated"

    val MOVIE_DETAIL: (movieId: String) -> String
        get() = { movieId -> "${BASE_URL}movie/$movieId" }

    val IMAGE_URL: (path: String) -> String
        get() = { path -> "https://image.tmdb.org/t/p/original$path" }
}