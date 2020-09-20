package com.example.moviesapp.ui.singlemoviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.api.POSTER_BASE_URL
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer { bindUI(it) })

        viewModel.networkState.observe(
            this,
            Observer {
                progress_bar.visibility =
                    if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            }
        )
    }

    private fun bindUI(movieDetails: MovieDetails) {
        movie_title.text = movieDetails.title
        movie_tagline.text = movieDetails.tagline
        movie_release_date.text = movieDetails.releaseDate
        movie_rating.text = movieDetails.rating.toString()
        movie_runtime.text = movieDetails.runtime.toString()
        movie_overview.text = movieDetails.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(movieDetails.budget)
        movie_revenue.text = formatCurrency.format(movieDetails.revenue)

        val moviePosterURL = POSTER_BASE_URL + movieDetails.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return SingleMovieViewModel(movieRepository, movieId) as T
                }
            }
        )[SingleMovieViewModel::class.java]
    }
}