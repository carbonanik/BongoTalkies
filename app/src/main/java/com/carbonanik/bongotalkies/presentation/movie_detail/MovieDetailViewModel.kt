package com.carbonanik.bongotalkies.presentation.movie_detail

import androidx.lifecycle.ViewModel
import com.carbonanik.bongotalkies.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

}