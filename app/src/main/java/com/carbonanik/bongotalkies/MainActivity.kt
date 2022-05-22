package com.carbonanik.bongotalkies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.carbonanik.bongotalkies.presentation.NavGraphs
import com.carbonanik.bongotalkies.presentation.movie_detail.MovieDetailViewModel
import com.carbonanik.bongotalkies.presentation.movie_list_screen.MovieListScreen
import com.carbonanik.bongotalkies.presentation.movie_list_screen.MovieListViewModel
import com.carbonanik.bongotalkies.ui.theme.BongoTalkiesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BongoTalkiesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}