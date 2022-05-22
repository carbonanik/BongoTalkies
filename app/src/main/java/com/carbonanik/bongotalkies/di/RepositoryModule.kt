package com.carbonanik.bongotalkies.di

import com.carbonanik.bongotalkies.data.remote.movie_api.MovieApi
import com.carbonanik.bongotalkies.data.remote.movie_detail_api.MovieDetailApi
import com.carbonanik.bongotalkies.repositories.MovieRepository
import com.carbonanik.bongotalkies.repositories.implementation.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi,
        movieDetailApi: MovieDetailApi
    ): MovieRepository {
        return MovieRepositoryImpl(movieApi, movieDetailApi)
    }

}