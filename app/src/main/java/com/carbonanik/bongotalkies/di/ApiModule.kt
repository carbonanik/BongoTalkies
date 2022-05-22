package com.carbonanik.bongotalkies.di

import com.carbonanik.bongotalkies.data.remote.const_.HttpRoutes
import com.carbonanik.bongotalkies.data.remote.movie_api.MovieApi
import com.carbonanik.bongotalkies.data.remote.movie_api.MovieApiImpl
import com.carbonanik.bongotalkies.data.remote.movie_detail_api.MovieDetailApi
import com.carbonanik.bongotalkies.data.remote.movie_detail_api.MovieDetailApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging){
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    @Provides
    @Singleton
    fun provideMovieApi(client: HttpClient): MovieApi {
        return MovieApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideMovieDetailApi(client: HttpClient): MovieDetailApi {
        return MovieDetailApiImpl()
    }
}